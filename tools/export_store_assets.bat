@echo off
setlocal

where inkscape >nul 2>nul
if errorlevel 1 (
  echo Fehler: Inkscape wurde nicht gefunden.
  echo Bitte Inkscape installieren und danach dieses Skript erneut starten.
  exit /b 1
)

set "REPO_ROOT=%~dp0.."
set "OUTPUT_DIR=%REPO_ROOT%\local-store-exports"
if not exist "%OUTPUT_DIR%" mkdir "%OUTPUT_DIR%"

inkscape "%REPO_ROOT%\store-assets\store-icon.svg" --export-type=png --export-filename="%OUTPUT_DIR%\store-icon-512.png" --export-width=512 --export-height=512
if errorlevel 1 exit /b 1

inkscape "%REPO_ROOT%\store-assets\feature-graphic.svg" --export-type=png --export-filename="%OUTPUT_DIR%\feature-graphic-1024.png" --export-width=1024 --export-height=500
if errorlevel 1 exit /b 1

echo.
echo Fertig. Die PNG-Dateien liegen im Ordner:
echo %OUTPUT_DIR%
endlocal

