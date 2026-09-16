#!/bin/bash

cd "$(dirname "$0")"

docker compose down

echo "Apache est arrêté."

read -p "Appuyez sur Entrée pour fermer..."
