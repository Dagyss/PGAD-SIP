terraform {
  required_version = ">= 0.13"
  required_providers {
    google = {
      source  = "hashicorp/google"
      version = "~> 4.0"
    }
  }
  backend "gcs" {
        bucket      = var.bucket_backend
        prefix      = var.backend_prefix
  }
}

# Variables

variable "bucket_backend" {
    type        = string
    description = "GCS bucket para el estado de Terraform"
}

variable "backend_prefix" {
    type        = string
    description = "Path/prefix dentro del bucket"
}

variable "project_id" {
  description = "ID del proyecto de GCP"
  type        = string
}

variable "region" {
  description = "Región de GCP"
  type        = string
  default     = "us-central1"
}

variable "zone" {
  description = "Zona de GCP para clúster zonal"
  type        = string
  default     = "us-central1-a"
}

variable "credentials_file" {
  description = "Ruta al archivo de credenciales (terraform-sa-key.json)"
  type        = string
}

variable "cluster_name" {
  description = "Nombre del clúster GKE"
  type        = string
  default     = "gke-cluster"
}

variable "machine_type" {
  description = "Tipo de máquina para los nodos"
  type        = string
  default     = "e2-medium"
}

variable "node_count" {
  description = "Número de nodos mínimo para pools estáticos"
  type        = number
  default     = 2
}

variable "boot_disk_size_gb" {
  description = "Tamaño del disco de arranque en GB"
  type        = number
  default     = 20
}

variable "disk_type" {
  description = "Tipo de disco de arranque"
  type        = string
  default     = "pd-standard"
}


variable "network" {
  description = "VPC donde desplegar clúster y VMs"
  type        = string
  default     = "default"
}

variable "subnetwork" {
  description = "Subred dentro de la VPC"
  type        = string
  default     = "default"
}


# Provider
provider "google" {
  credentials = file(var.credentials_file)
  project     = var.project_id
  region      = var.region
  zone        = var.zone
}

resource "google_project_service" "container" {
  service = "container.googleapis.com"
}
resource "google_project_service" "iam" {
  service = "iam.googleapis.com"
}
resource "google_project_service" "iamcredentials" {
  service = "iamcredentials.googleapis.com"
}

# VPC and Subnet
resource "google_compute_network" "vpc" {
  name                    = var.network
  auto_create_subnetworks = false
}

resource "google_compute_subnetwork" "subnet" {
  name          = var.subnetwork
  ip_cidr_range = "10.0.0.0/16"
  region        = var.region
  network       = google_compute_network.vpc.id

  secondary_ip_range {
    range_name    = "gke-cluster-secondary-range"
    ip_cidr_range = "10.1.0.0/20"
  }

  secondary_ip_range {
    range_name    = "gke-services-secondary-range"
    ip_cidr_range = "10.2.0.0/20"
  }
}

# GKE Cluster
resource "google_container_cluster" "primary" {
  name                     = var.cluster_name
  location                 = var.zone
  remove_default_node_pool = true
  networking_mode          = "VPC_NATIVE"
  network                  = google_compute_network.vpc.name
  subnetwork               = google_compute_subnetwork.subnet.name
  initial_node_count       = 1

  ip_allocation_policy {
    cluster_secondary_range_name  = "gke-cluster-secondary-range"
    services_secondary_range_name = "gke-services-secondary-range"
  }

  node_config {
    machine_type = var.machine_type
    disk_size_gb = var.boot_disk_size_gb
    disk_type    = var.disk_type
    oauth_scopes = ["https://www.googleapis.com/auth/cloud-platform"]
  }

  workload_identity_config {
    workload_pool = "${var.project_id}.svc.id.goog"
  }
}


# Node pool apps
resource "google_container_node_pool" "apps" {
  name     = "${var.cluster_name}-apps"
  cluster  = google_container_cluster.primary.name
  location = var.zone
  node_config {
    machine_type = var.machine_type
    disk_size_gb  = var.boot_disk_size_gb
    disk_type     = var.disk_type
    labels        = { role = "app" }
    oauth_scopes = ["https://www.googleapis.com/auth/cloud-platform"]
  }
  autoscaling {
    min_node_count = var.node_count
    max_node_count = var.node_count * 2
  }
}

resource "google_service_account" "pga_sa" {
  account_id   = "pga-sa"
  display_name = "Service Account para PGA Backend"
}


resource "google_project_service" "crm" {
  service = "cloudresourcemanager.googleapis.com"
}

resource "google_project_service" "vertex_ai_api" {
  service = "aiplatform.googleapis.com"
  depends_on = [google_project_service.crm]
}

resource "google_project_service" "generative_api" {
  service = "generativelanguage.googleapis.com"
  depends_on = [google_project_service.crm]
}

resource "google_project_iam_member" "vertex_ai_user" {
  project = var.project_id
  role    = "roles/aiplatform.user"
  member  = "serviceAccount:${google_service_account.pga_sa.email}"
  depends_on = [
    google_project_service.crm,
    google_project_service.vertex_ai_api
  ]
}

resource "google_service_account_iam_member" "workload_identity_binding" {
  service_account_id = google_service_account.pga_sa.name
  role               = "roles/iam.workloadIdentityUser"
  member             = "serviceAccount:${var.project_id}.svc.id.goog[pga-backend/pga-sa]"
}


# Reglas de Firewall
resource "google_compute_firewall" "allow-ssh" {
  name    = "allow-ssh"
  network = google_compute_network.vpc.name

  allow {
    protocol = "tcp"
    ports    = ["22"]
  }

  source_ranges = ["0.0.0.0/0"]
}

resource "google_compute_firewall" "allow-http" {
  name    = "allow-http"
  network = google_compute_network.vpc.name

  allow {
    protocol = "tcp"
    ports    = ["80"]
  }

  source_ranges = ["0.0.0.0/0"]
}

resource "google_compute_firewall" "allow-https" {
  name    = "allow-https"
  network = google_compute_network.vpc.name

  allow {
    protocol = "tcp"
    ports    = ["443"]
  }

  source_ranges = ["0.0.0.0/0"]
}

resource "tls_private_key" "ssh_key" {
  algorithm = "RSA"
  rsa_bits  = 4096
}

resource "local_file" "ssh_private_key_pem" {
  content         = tls_private_key.ssh_key.private_key_pem
  filename        = ".ssh/google_compute_engine"
  file_permission = "0600"
}
