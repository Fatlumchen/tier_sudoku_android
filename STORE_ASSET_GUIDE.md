# Store-Grafiken und Screenshots

## Vorbereitete Quellen

- `store-assets/store-icon.svg`: quadratische 512×512-Quelle für das Play-Store-Icon.
- `store-assets/feature-graphic.svg`: Feature-Grafik im Seitenverhältnis 1024×500.

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

