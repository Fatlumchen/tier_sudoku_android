# Store-Grafiken und Screenshots

## Vorbereitete Quellen

- `store-assets/store-icon.svg`: quadratische 512×512-Quelle für das Play-Store-Icon.
- `store-assets/feature-graphic.svg`: Feature-Grafik im Seitenverhältnis 1024×500.

### Was bedeutet SVG → PNG?

Eine **SVG-Datei** ist die bearbeitbare, beliebig skalierbare Originalzeichnung. Google
Play erwartet für diese beiden Upload-Felder jedoch eine fertige **PNG-Bilddatei**.
Beim Export wird die SVG-Zeichnung lediglich in ein normales Bild mit einer festen
Pixelgröße umgewandelt. Die SVG-Datei wird dabei nicht gelöscht oder ersetzt.

Diese beiden Dateien sind **keine App-Screenshots**:

- `store-icon-512.png` ist nur das quadratische Symbol im Play-Store-Eintrag.
- `feature-graphic-1024.png` ist das breite Werbebild oberhalb des Store-Eintrags.
- Screenshots werden später separat direkt vom laufenden Emulator aufgenommen.

Die SVG-Dateien sind editierbare Quellen. Vor dem Upload in die Play Console müssen sie
im Browser, in Inkscape, Affinity Designer oder einem vergleichbaren Werkzeug geöffnet,
visuell geprüft und als **PNG ohne Größenänderung** exportiert werden:

```text
store-icon.svg       → store-icon-512.png       (512 × 512 Pixel)
feature-graphic.svg  → feature-graphic-1024.png  (1024 × 500 Pixel)
```

Die exportierten PNGs werden bewusst noch nicht in Git eingecheckt, damit der Pull
Request frei von Binärdateien bleibt. Die endgültigen PNGs sollten separat in die Play
Console hochgeladen und sicher archiviert werden.

## Einfachster Export mit Inkscape

[Inkscape](https://inkscape.org/) ist ein kostenloses Programm zum Öffnen und
Exportieren von SVG-Dateien.

### Windows

1. Inkscape installieren und danach gegebenenfalls Windows neu starten.
2. Den entpackten beziehungsweise geklonten Projektordner öffnen.
3. Die Datei `tools/export_store_assets.bat` doppelt anklicken.
4. Nach erfolgreichem Abschluss den neu erstellten Ordner
   `local-store-exports` öffnen.

Dort liegen anschließend:

```text
local-store-exports/store-icon-512.png
local-store-exports/feature-graphic-1024.png
```

### macOS oder Linux

Im Terminal in den Projektordner wechseln und ausführen:

```bash
./tools/export_store_assets.sh
```

Auch hier werden beide PNG-Dateien im Ordner `local-store-exports/` abgelegt.

### Manuell in Inkscape

Falls das Skript nicht verwendet werden soll:

1. Inkscape starten.
2. **Datei → Öffnen** wählen und zunächst `store-assets/store-icon.svg` öffnen.
3. **Datei → Exportieren** wählen.
4. Als Bereich **Seite** und als Format **PNG** wählen.
5. Prüfen, dass Breite und Höhe jeweils `512 px` betragen.
6. Als Dateinamen `store-icon-512.png` wählen und **Exportieren** drücken.
7. Danach `store-assets/feature-graphic.svg` öffnen und dieselben Schritte mit
   `1024 px` Breite und `500 px` Höhe wiederholen.

Die exportierten Dateien können per Doppelklick wie jedes andere Bild kontrolliert
werden. Erst wenn Text, Farben und Ränder korrekt aussehen, werden sie in der Play
Console hochgeladen.

## Screenshot-Aufnahme

Vor der Aufnahme:

1. Release-nahen Build ohne Debug-Einblendungen verwenden.
2. Emulator oder Gerät auf Deutsch stellen.
3. Uhrzeit, Sterne, Bestzeiten und Fortschritt auf plausible Beispielwerte bringen.
4. Keine Mauszeiger, Emulatorrahmen, Entwicklungsfenster oder persönlichen
   Benachrichtigungen abbilden.
5. Ausschließlich tatsächlich vorhandene Funktionen zeigen.

Empfohlene Motive:

1. moderne Spielauswahl;
2. Auswahl der drei Tierwelten;
3. Tierfreunde-Sudoku;
4. Bauernhof- oder Unterwasser-Sudoku;
5. klassisches 4×4-Sudoku;
6. klassisches 9×9-Sudoku mit klaren 3×3-Grenzen;
7. Fortschritt, Serien und Abzeichen;
8. Eltern- und Datenschutzinformation.

Nach der Aufnahme jeden Screenshot bei 100 % kontrollieren: Texte dürfen nicht
abgeschnitten sein, Rasterlinien müssen scharf erscheinen und alle Symbole müssen
innerhalb der Bildschirmränder liegen.
