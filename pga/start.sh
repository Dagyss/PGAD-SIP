#!/bin/bash

# Colores para mensajes
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Función para verificar comandos disponibles
check_command() {
    if ! command -v $1 &> /dev/null; then
        echo -e "${RED}Error: El comando '$1' no está instalado.${NC}"
        exit 1
    fi
}

# Verificar dependencias
echo -e "${YELLOW}Verificando dependencias...${NC}"
check_command docker
check_command docker-compose
check_command mvn

# Limpiar ambiente previo
echo -e "${YELLOW}Limpiando ambiente previo...${NC}"
docker-compose down -v

# Construir proyecto
echo -e "${YELLOW}Construyendo aplicación con Maven...${NC}"
mvn clean package -DskipTests #> logs/maven.log 2>&1

# Construir y levantar solo la base de datos primero
echo -e "${YELLOW}Iniciando PostgreSQL...${NC}"
docker-compose up -d --build pga-db

# Esperar a que PostgreSQL esté listo
echo -e "${YELLOW}Esperando a que PostgreSQL esté listo...${NC}"
sleep 5

# Construir y levantar el backend
echo -e "${YELLOW}Iniciando backend...${NC}"
docker-compose up -d --build pga-backend

# Verificación final
echo -e "\n${GREEN}¡Aplicación desplegada correctamente!${NC}"
echo -e "Accede a la aplicación en: http://localhost:${PORT}"