#!/usr/bin/env bash
# Fonctions communes à tous les scripts de la pipeline.
set -uo pipefail

CI_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(cd "$CI_DIR/.." && pwd)"
LOG_DIR="$CI_DIR/logs"
mkdir -p "$LOG_DIR"

LOG_FILE="${LOG_FILE:-$LOG_DIR/standalone.log}"
export LOG_FILE

# Les projets Maven du dépôt
MODULES=(Backend/Employe Backend/Entreprise)

cd "$PROJECT_DIR" || exit 1

# log <NIVEAU> <message> : écrit à l'écran ET dans le fichier de logs
log() {
  printf '%s [%s] %s\n' "$(date '+%Y-%m-%d %H:%M:%S')" "$1" "$2" | tee -a "$LOG_FILE"
}

# run <commande...> : exécute, envoie la sortie dans les logs, renvoie le vrai code de retour
run() {
  log CMD "$*"
  "$@" 2>&1 | tee -a "$LOG_FILE"
  return "${PIPESTATUS[0]}"
}

# fail <message> <code> : log l'erreur et sort avec un code d'erreur
fail() {
  log ERROR "$1"
  exit "${2:-1}"
}

# mvn_module <module> <args maven...> : lance Maven dans un module
# (utilise le mvnw du module s'il existe, sinon le mvn installé)
mvn_module() {
  local mod="$1"; shift
  local cmd=(mvn)
  [ -f "$PROJECT_DIR/$mod/mvnw" ] && cmd=(sh ./mvnw)
  log INFO "--- Module $mod ---"
  ( cd "$PROJECT_DIR/$mod" && run "${cmd[@]}" "$@" )
}

# mvn_all <args maven...> : lance Maven sur tous les modules, stoppe au premier échec
mvn_all() {
  local m
  for m in "${MODULES[@]}"; do
    mvn_module "$m" "$@" || { log ERROR "Échec sur le module $m"; return 1; }
  done
}
