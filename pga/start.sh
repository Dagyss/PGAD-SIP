#!/bin/bash

# Colores para mensajes
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

export COMPOSE_DOCKER_CLI_BUILD=1
export DOCKER_BUILDKIT=1

# Función para verificar comandos disponibles
check_command() {
    if ! command -v $1 &> /dev/null; then
        echo -e "${RED}Error: El comando '$1' no está instalado.${NC}"
        exit 1
    fi
}

# Función para verificar y crear volúmenes si no existen
ensure_volume_exists() {
    VOLUME_NAME=$1
    if ! docker volume ls --format '{{.Name}}' | grep -q "^${VOLUME_NAME}$"; then
        echo -e "${YELLOW}El volumen '${VOLUME_NAME}' no existe. Creándolo...${NC}"
        docker volume create "$VOLUME_NAME" > /dev/null
        echo -e "${GREEN}Volumen '${VOLUME_NAME}' creado.${NC}"
    else
        echo -e "${GREEN}Volumen '${VOLUME_NAME}' ya existe.${NC}"
    fi
}

# Verificar dependencias
echo -e "${YELLOW}Verificando dependencias...${NC}"
check_command docker
check_command docker-compose
check_command mvn
check_command sha256sum

# Verificar volúmenes
ensure_volume_exists "postgres-data"
ensure_volume_exists "ollama-data"

# Limpiar ambiente previo
echo -e "${YELLOW}Limpiando ambiente previo...${NC}"
docker-compose down -v
docker-compose stop pga-backend

# Construir proyecto
echo -e "${YELLOW}Construyendo aplicación con Maven...${NC}"
mvn package -DskipTests #> logs/maven.log 2>&1

JAR_FILE="target/plataforma-aprendizaje-ai-1.0-SNAPSHOT.jar"
HASH_FILE=".last_jar_hash"

# Calcular hash del nuevo JAR
NEW_HASH=$(sha256sum "$JAR_FILE" | awk '{ print $1 }')
BUILD_BACKEND=false

# Comparar con hash anterior
if [ -f "$HASH_FILE" ]; then
    LAST_HASH=$(cat "$HASH_FILE")
    if [ "$LAST_HASH" != "$NEW_HASH" ]; then
        BUILD_BACKEND=true
    fi
else
    BUILD_BACKEND=true
fi

# Guardar nuevo hash
echo "$NEW_HASH" > "$HASH_FILE"

# Construir y levantar solo la base de datos primero
echo -e "${YELLOW}Iniciando PostgreSQL...${NC}"
docker-compose up -d pga-db

# Esperar a que PostgreSQL esté listo
echo -e "${YELLOW}Esperando a que PostgreSQL esté listo...${NC}"
sleep 4

# Construir y levantar el backend
if [ "$BUILD_BACKEND" = true ]; then
    echo -e "${YELLOW}Detectado cambio en el JAR. Reconstruyendo imagen del backend...${NC}"
    docker-compose up -d --build pga-backend
else
    echo -e "${GREEN}No hubo cambios en el JAR. Usando imagen del backend en caché.${NC}"
    docker-compose up -d pga-backend
fi

# Levantar servicio de Ollama
docker-compose up -d ollama

# Verificación final
echo -e "\n${GREEN}¡Aplicación desplegada correctamente!${NC}"