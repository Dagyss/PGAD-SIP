#!/bin/bash

# Inicia Ollama en segundo plano
ollama serve &

# Espera a que Ollama esté disponible
until curl -s http://localhost:11434; do
  echo "Esperando que Ollama arranque..."
  sleep 1
done

# Verifica si el modelo ya está descargado
if ! ollama list | grep -q "mistral:7b-instruct"; then
  echo "Descargando modelo mistral:7b-instruct..."
  ollama pull mistral:7b-instruct
else
  echo "El modelo mistral:7b-instruct ya está presente."
fi

# Ejecuta el modelo
ollama run mistral:7b-instruct

# Mantiene el contenedor en ejecución
tail -f /dev/null