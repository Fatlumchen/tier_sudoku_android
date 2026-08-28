#!/usr/bin/env bash
set -euo pipefail

if ! command -v inkscape >/dev/null 2>&1; then
  echo "Fehler: Inkscape wurde nicht gefunden."
  echo "Bitte Inkscape installieren und diesen Befehl danach erneut ausführen."
  exit 1
fi

repo_root="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
output_dir="$repo_root/local-store-exports"
mkdir -p "$output_dir"

inkscape "$repo_root/store-assets/store-icon.svg" \
  --export-type=png \
  --export-filename="$output_dir/store-icon-512.png" \
  --export-width=512 \
  --export-height=512

inkscape "$repo_root/store-assets/feature-graphic.svg" \
  --export-type=png \
  --export-filename="$output_dir/feature-graphic-1024.png" \
  --export-width=1024 \
  --export-height=500

echo
echo "Fertig. Die Dateien liegen hier:"
echo "  $output_dir/store-icon-512.png"
echo "  $output_dir/feature-graphic-1024.png"

