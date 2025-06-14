#!/bin/bash
kubectl apply -f deploy/namespaces/
kubectl apply -f deploy/secrets/
kubectl apply -f deploy/serviceaccount/
kubectl apply -f deploy/configmaps/
kubectl apply -f deploy/services/
kubectl apply -f deploy/statefulset/
kubectl apply -f deploy/deployment/
kubectl apply -f deploy/ingress/
kubectl apply -f deploy/clusterIssuer/
kubectl apply -f deploy/certificate/