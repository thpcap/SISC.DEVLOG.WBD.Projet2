#!/bin/bash

cd "$(dirname "$0")"

docker compose up -d

echo "Apache est démarré."
echo "http://localhost:8080"

read -p "Appuyez sur Entrée pour fermer..."
