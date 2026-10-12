#!/usr/bin/env bash
# Script global : exécute chaque étape dans l'ordre, s'arrête à la première erreur.
# Usage (depuis la racine du projet) : ./ci/main.sh
CI_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
mkdir -p "$CI_DIR/logs"
export LOG_FILE="$CI_DIR/logs/pipeline_$(date +%Y%m%d_%H%M%S).log"

STEPS=(checkout validate dependencies compile test quality coverage package verify deploy)

echo "$(date '+%Y-%m-%d %H:%M:%S') [PIPELINE] Début - log : $LOG_FILE" | tee -a "$LOG_FILE"
START=$(date +%s)

for step in "${STEPS[@]}"; do
  bash "$CI_DIR/$step.sh"
  RC=$?
  if [ "$RC" -ne 0 ]; then
    echo "$(date '+%Y-%m-%d %H:%M:%S') [PIPELINE] ÉCHEC à l'étape '$step' (code $RC)" | tee -a "$LOG_FILE"
    exit "$RC"
  fi
  echo "$(date '+%Y-%m-%d %H:%M:%S') [PIPELINE] Étape '$step' OK" >> "$LOG_FILE"
done

echo "$(date '+%Y-%m-%d %H:%M:%S') [PIPELINE] SUCCÈS en $(( $(date +%s) - START )) s" | tee -a "$LOG_FILE"
