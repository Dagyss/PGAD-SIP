# GCP
project_id       = "pgad-sip"
credentials_file = "terraform-sa-key.json"

# Backend
bucket_backend   = "bucket-backend-pgad-sip"
backend_prefix   = "terraform/tfstate"

# VPC / Subnet donde corre el cluster
network    = "vpc-pga-network"
subnetwork = "subnetwork-pga"
