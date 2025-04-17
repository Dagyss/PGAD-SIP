#!/bin/bash
echo "Construyendo con Maven..."
mvn clean package

echo "Iniciando contenedores con Docker Compose..."
docker-compose up --build