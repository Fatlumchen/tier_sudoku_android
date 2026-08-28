# Tier-Sudoku

Ein bewusst kleines, offline spielbares Android-MVP für Kinder. Auf dem Startbildschirm
kann zwischen zwei Spielarten gewählt werden:

- Beim **Tier-Sudoku** fehlt in einem 4×4-Raster ein Tier. Das Kind wählt das passende
  Tier aus und sammelt Sterne.
- Beim **klassischen Sudoku** werden Zahlen ergänzt. Zur Auswahl stehen eine leichte
  Variante mit 2×2-Blöcken (4×4 Felder) und die bekannte Variante mit 3×3-Blöcken
  (9×9 Felder). Das 9×9-Sudoku bietet die Stufen Leicht, Mittel und Schwer. Zahlen,
  Zeilen und Spalten werden für jede Runde neu gemischt; jedes Rätsel besitzt genau
  eine Lösung.
- Unter **Meine Erfolge** zeigt die App dauerhaft gespeicherte Bestzeiten, gelöste
  Runden und gesammelte Sterne getrennt nach Spielart und Schwierigkeitsgrad an.
- Kurze, direkt als PCM-Audio auf dem Gerät erzeugte Signaltöne geben Rückmeldung zu richtigen und
  falschen Antworten. Sie können auf dem Startbildschirm dauerhaft ausgeschaltet werden;
  es werden keine externen oder lizenzpflichtigen Audiodateien mitgeliefert.
- Die vier Tierfiguren sind als eigens für das Projekt erstellte, skalierbare
  Android-Vektorgrafiken enthalten. Dadurch bleiben sie auf allen Displaygrößen scharf
  und benötigen keine extern lizenzierten Bilddateien.
- Die Fortschrittsseite enthält Serien und vier lokal freischaltbare Abzeichen für
  gelöste Runden, gesammelte Sterne und fehlerfreies Spielen.
- Das Tier-Sudoku bietet die Welten **Tierfreunde**, **Bauernhof** und
  **Unterwasserwelt** mit insgesamt zwölf eigenen Vektortieren, eigener Farbwelt und
  getrennten Bestzeiten.

Die dauerhaft vorgesehene Android-Application-ID lautet
`de.fatljumneziri.tiersudoku`. **Tier-Sudoku** bleibt dabei der Markenname der App;
das klassische Zahlen-Sudoku ist eine zusätzliche Spielart innerhalb derselben App.

> Falls im Emulator kein Ton hörbar ist: Im Android-Emulator die Lautsprecher-Schaltfläche
> aktivieren und unter Android **Einstellungen → Ton & Vibration → Medienlautstärke** prüfen.
> Die App verwendet bewusst die Medien-/Spielaudio-Ausgabe und nicht die Klingeltonlautstärke.

Eine projektspezifische Prüfung der noch offenen Sicherheits-, Familien- und
Veröffentlichungsschritte steht in [`PLAY_STORE_CHECKLIST.md`](PLAY_STORE_CHECKLIST.md).
Die App enthält außerdem auf dem Startbildschirm eine lokale Seite **Für Eltern &amp;
Datenschutz** mit einer verständlichen Übersicht der aktuellen Datenverarbeitung.
Die ausgefüllte Ausgangsfassung der noch unter HTTPS zu veröffentlichenden Erklärung
liegt in [`PRIVACY_POLICY.md`](PRIVACY_POLICY.md).
Eine fertige statische Support- und Datenschutzseite für GitHub Pages liegt unter
[`docs/`](docs/). Nach Aktivierung von GitHub Pages ist die Datenschutzseite
voraussichtlich unter `https://fatlumchen.github.io/tier_sudoku_android/datenschutz.html`
erreichbar.
Der vorbereitete deutsche Play-Store-Text und die Screenshot-Liste liegen in
[`STORE_LISTING_DE.md`](STORE_LISTING_DE.md). Die App enthält außerdem ein adaptives
Launcher-Icon für moderne Android-Geräte und passende Fallback-Icons.
Editierbare Quellen für das 512×512-Store-Icon und die 1024×500-Feature-Grafik liegen
unter [`store-assets/`](store-assets/); Export- und Screenshot-Hinweise stehen in
[`STORE_ASSET_GUIDE.md`](STORE_ASSET_GUIDE.md).
Mit installiertem Inkscape erzeugen `tools/export_store_assets.bat` unter Windows bzw.
`./tools/export_store_assets.sh` unter macOS/Linux automatisch die benötigten PNGs.

### Kostenlose Webseite mit GitHub Pages aktivieren

1. Das Repository zu GitHub pushen.
2. Auf GitHub **Settings → Pages** öffnen.
3. Unter **Build and deployment → Source** die Option **GitHub Actions** wählen.
4. Unter **Actions** den Workflow **Datenschutzseite veröffentlichen** öffnen. Falls er
   noch nicht automatisch lief, **Run workflow** wählen und den Branch `main` starten.
5. Warten, bis der Workflow und insbesondere der Schritt **GitHub Pages bereitstellen**
   grün abgeschlossen sind.
6. Die unter **Settings → Pages** angezeigte Adresse öffnen. Die erste Bereitstellung
   kann einige Minuten dauern; ein 404 davor ist normal.
7. `datenschutz.html` im Browser öffnen und erst nach erfolgreicher Prüfung als
   Datenschutz-URL in der Play Console eintragen.

#### Wenn weiterhin „404 – Page not found“ erscheint

1. Prüfen, ob `docs/index.html` und `docs/datenschutz.html` wirklich auf dem GitHub-Branch
   `main` sichtbar sind. Lokale, noch nicht gepushte Dateien kann GitHub Pages nicht sehen.
2. Unter **Actions** prüfen, ob der Pages-Workflow grün ist. Bei einem roten Lauf die
   Fehlermeldung öffnen, statt die URL erneut zu laden.
3. Unter **Settings → Pages** sicherstellen, dass **Source: GitHub Actions** ausgewählt ist.
4. Genau diese URLs verwenden (Repository-Name inklusive Unterstrichen):
   - `https://fatlumchen.github.io/tier_sudoku_android/`
   - `https://fatlumchen.github.io/tier_sudoku_android/datenschutz.html`
5. Nach einem erfolgreichen ersten Deployment zwei bis zehn Minuten warten und die
   Seite in einem privaten Browserfenster erneut öffnen.

## Warum dieses MVP?

- **Sofort verständlich:** keine Anmeldung, keine Texteingabe, kurze Spielrunden.
- **Kinderfreundlich:** keine Werbung, kein Tracking und keine Internet-Berechtigung.
- **Monetarisierbar ohne Dark Patterns:** sinnvoll wäre später ein einmaliger, durch
  eine Elternschranke geschützter Kauf für zusätzliche Tierwelten und Schwierigkeitsgrade.

> Einnahmen sind nicht garantiert. Vor einer Veröffentlichung müssen insbesondere
> die Google-Play-Richtlinien für Familien, Datenschutzangaben, Altersfreigabe,
> Store-Grafiken und eine echte Elternschranke geprüft und ergänzt werden.

## Repository herunterladen oder klonen

Du brauchst nur **eine** der folgenden Möglichkeiten. Wenn du Git noch nie benutzt
hast, ist Variante A am einfachsten.

### Variante A: Als ZIP-Datei herunterladen

1. Öffne im Browser die Webseite dieses Repositorys (die Seite, auf der du diese
   README-Datei siehst).
2. Klicke oberhalb der Dateiliste auf die grüne Schaltfläche **Code**.
3. Klicke auf **Download ZIP** und speichere die Datei auf deinem Computer.
4. Öffne deinen Download-Ordner und entpacke die ZIP-Datei:
   - **Windows:** Rechtsklick auf die ZIP-Datei → **Alle extrahieren**.
   - **macOS:** Doppelklick auf die ZIP-Datei.
   - **Linux:** Rechtsklick → **Hier entpacken**.
5. Nach dem Entpacken erhältst du einen Projektordner. Er kann beispielsweise
   `tier_sudoku_android-main` heißen. Diesen **entpackten Ordner** öffnest du später
   in Android Studio – nicht die ZIP-Datei.

### Variante B: Mit Git klonen

Voraussetzung ist eine installierte Git-Version. Ob Git vorhanden ist, kannst du in
PowerShell, Terminal oder Eingabeaufforderung prüfen:

```bash
git --version
```

Danach gehst du auf der Repository-Webseite auf **Code**, kopierst dort die angezeigte
HTTPS-Adresse und führst folgende Befehle aus:

```bash
# Wechsle beispielsweise in deinen Dokumente-Ordner.
cd Documents

# Ersetze <REPOSITORY-URL> durch die kopierte HTTPS-Adresse.
git clone <REPOSITORY-URL>

# Wechsle in den heruntergeladenen Projektordner.
cd tier_sudoku_android
```

Ein vollständiges Beispiel für eine GitHub-Adresse sieht so aus:

```bash
git clone https://github.com/DEIN-BENUTZERNAME/tier_sudoku_android.git
cd tier_sudoku_android
```

`DEIN-BENUTZERNAME` ist dabei nur ein Platzhalter. Verwende die Adresse, die dir über
die **Code**-Schaltfläche deines Repositorys angezeigt wird. Bei einem privaten
Repository kann GitHub zusätzlich eine Anmeldung verlangen.

### Variante C: Direkt mit Android Studio klonen

1. Starte Android Studio.
2. Wähle im Startfenster **Get from VCS**. Wenn bereits ein Projekt geöffnet ist,
   findest du dieselbe Funktion unter **File → New → Project from Version Control**.
3. Wähle als Versionskontrolle **Git**.
4. Füge in das Feld **URL** die HTTPS-Adresse aus der **Code**-Schaltfläche ein.
5. Wähle unter **Directory** den gewünschten Speicherort aus.
6. Klicke auf **Clone** und warte, bis Android Studio das Projekt geöffnet hat.

## Starten

1. Führe zuerst eine der Download- oder Klon-Varianten oben aus.
2. In Android Studio **File → Open** wählen und den entpackten bzw. geklonten
   Projektordner öffnen (zum Beispiel `tier_sudoku_android` oder
   `tier_sudoku_android-main`) – nicht nur den Unterordner `app`. Der richtige Ordner
   enthält unter anderem `settings.gradle.kts`, `build.gradle.kts` und den Ordner `app`.
3. Warten, bis die Gradle-Synchronisierung abgeschlossen ist.
4. Android SDK 35 über den SDK Manager installieren bzw. auswählen.
5. Einen Emulator oder ein Gerät ab Android 6.0 auswählen und **Run 'app'** drücken.

Es müssen keine Dateien manuell in Android Studio kopiert werden. Die Verzeichnisstruktur
des Repositorys ist bereits die vollständige Projektstruktur.

## Wo gehören die Dateien hin?

Alle Pfade in dieser Anleitung beginnen im Projekt-Hauptordner
`tier_sudoku_android/`. Die Dateien müssen genau in dieser Struktur liegen:

```text
tier_sudoku_android/                 ← diesen Ordner in Android Studio öffnen
├── settings.gradle.kts              ← registriert das app-Modul
├── build.gradle.kts                 ← Android-Plugin-Version für das Gesamtprojekt
├── gradle.properties                ← globale Gradle-/Android-Einstellungen
├── gradlew                           ← Gradle-Startdatei für macOS/Linux
├── gradlew.bat                       ← Gradle-Startdatei für Windows
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
└── app/                              ← das eigentliche Android-App-Modul
    ├── build.gradle.kts              ← SDK, App-ID und Abhängigkeiten
    ├── proguard-rules.pro            ← Regeln für optimierte Release-Builds
    └── src/
        ├── main/
        │   ├── AndroidManifest.xml   ← App- und Start-Activity-Registrierung
        │   ├── java/de/tiersudoku/app/
        │   │   ├── MainActivity.java ← Oberfläche und Benutzerinteraktion
        │   │   └── GameEngine.java   ← Rätselerzeugung und Antwortprüfung
        │   └── res/values/
        │       ├── strings.xml       ← sichtbare Texte
        │       ├── colors.xml        ← Farbdefinitionen
        │       └── themes.xml        ← Erscheinungsbild der App
        └── test/java/de/tiersudoku/app/
            └── GameEngineTest.java   ← lokale Unit-Tests
```

### Falls du ein neues Android-Studio-Projekt verwendest

Am einfachsten ist es, **dieses Repository direkt zu öffnen**. Wenn du die Dateien
trotzdem in ein bereits vorhandenes Projekt übernehmen willst:

1. Kopiere `MainActivity.java` und `GameEngine.java` nach
   `app/src/main/java/de/tiersudoku/app/`.
2. Kopiere die drei XML-Dateien nach `app/src/main/res/values/` und ersetze dort
   gleichnamige Dateien nur, wenn du deren bisherigen Inhalt nicht mehr brauchst.
3. Übernimm den Inhalt von `AndroidManifest.xml` nach `app/src/main/AndroidManifest.xml`.
4. Gleiche `app/build.gradle.kts` mit dem vorhandenen Modul ab. Nicht blind ersetzen,
   falls dein Projekt bereits zusätzliche Plugins oder Abhängigkeiten enthält.
5. Behalte `namespace` und die Java-`package`-Zeilen als `de.tiersudoku.app` bei. Die
   öffentliche Play-Store-ID `de.fatljumneziri.tiersudoku` steht ausschließlich bei
   `applicationId`; beide Werte dürfen sich absichtlich unterscheiden.
6. Lege den Test unter `app/src/test/java/de/tiersudoku/app/` ab.

Die Trennung verhindert unnötige Java- und `R`-Klassenfehler bei einer Änderung der
Play-Store-ID. Nur `applicationId` bestimmt die Identität der installierten App.

## Tests

Im Projekt-Hauptordner ausführen:

```bash
# macOS/Linux
./gradlew test

# Windows
gradlew.bat test
```

Die Testergebnisse liegen anschließend unter `app/build/reports/tests/`.

## Sinnvolle nächste Schritte

1. Optionale tägliche Herausforderungen mit wechselnden Spielarten ergänzen.
2. Weitere Abzeichen und kindgerechte Belohnungsanimationen ergänzen.
3. Datenschutz-/Eltern-Info entsprechend der Play-Store-Checkliste integrieren.
4. Einmaligen In-App-Kauf über Google Play Billing integrieren.
5. Barrierefreiheit, Geräte-Tests und eine deutsche Datenschutzerklärung abschließen.
