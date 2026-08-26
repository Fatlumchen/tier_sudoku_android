# Google-Play- und Sicherheitscheckliste

Stand dieser Projektprüfung: 25. August 2026. Richtlinien und Fristen ändern sich;
vor jeder Veröffentlichung müssen die verlinkten Originalrichtlinien und die Hinweise
in der Play Console erneut geprüft werden.

## Aktueller Sicherheitsstand

Bereits positiv umgesetzt:

- Die App fordert keine Internet-, Standort-, Kamera-, Mikrofon-, Kontakte- oder
  Speicherberechtigung an.
- Es gibt keine Werbung, Analyse-SDKs, Tracker, Anmeldung oder externen Links.
- Fortschritt, Einstellungen und Bestzeiten bleiben lokal in privaten
  `SharedPreferences` der App.
- Es werden keine nativen Bibliotheken und keine fremden Audio- oder Bilddateien geladen.
- Nur die Launcher-Activity ist exportiert; es existieren keine exportierten Dienste,
  Broadcast Receiver oder Content Provider.

Vor dem öffentlichen Release noch erforderlich oder dringend empfohlen:

- App-Backup ist bewusst deaktiviert, sodass lokale Fortschrittsdaten nicht über das
  Android-Backup außerhalb der App wiederhergestellt werden.
- Release ausschließlich als signiertes Android App Bundle (`.aab`) erstellen und
  **Play App Signing** verwenden. Upload-Schlüssel niemals in Git einchecken.
- Abhängigkeiten vor jedem Release aktualisieren und mit Android Studio/Lint sowie dem
  Play-Console-Pre-Launch-Report prüfen.
- App auf echten Geräten, unterschiedlichen Displaygrößen, TalkBack und mindestens den
  unterstützten Android-Hauptversionen testen.

## Für eine Kinder-App zwingend zu prüfen

### 1. Zielgruppe und Familienrichtlinien

In **Play Console → App-Inhalte → Zielgruppe und Inhalte** müssen die tatsächlichen
Altersgruppen korrekt angegeben werden. Sobald Kinder zur Zielgruppe gehören, gelten
die [Google-Play-Richtlinien für Familien](https://support.google.com/googleplay/android-developer/answer/9893335).

Für dieses Projekt bedeutet das insbesondere:

- keine personalisierte Werbung und keine manipulativen Kaufaufforderungen;
- nur für Kinder zulässige SDKs einsetzen;
- externe Links und Käufe ausschließlich in einem Elternbereich platzieren;
- Store-Text, Screenshots und Grafiken dürfen das tatsächliche Spiel nicht irreführend
  darstellen;
- eine verständliche Datenschutzerklärung bereitstellen.

Die App enthält aktuell keine Werbung oder Käufe. Das ist für den ersten Release die
risikoärmste Variante.

### 2. Datenschutzerklärung und Datensicherheitsformular

Auch wenn keine personenbezogenen Daten erhoben werden, müssen in der Play Console das
Formular **Datensicherheit** und die Fragen zu den Datenpraktiken wahrheitsgemäß
ausgefüllt werden. Maßgeblich ist die
[Google-Play-Anleitung zum Datensicherheitsformular](https://support.google.com/googleplay/android-developer/answer/10787469).

Vor dem Upload fehlt noch:

- eine öffentlich erreichbare HTTPS-Seite mit der Datenschutzerklärung;
- ein Link auf diese Seite im Store-Eintrag;
- ~~eine leicht erreichbare Datenschutz-/Info-Seite innerhalb der App;~~ umgesetzt;
- eine Erklärung, dass Spielstände, Sterne, Bestzeiten, Serien und Toneinstellung nur
  lokal gespeichert werden und wie sie durch Löschen der App entfernt werden können;
- ~~Kontaktdaten des verantwortlichen Entwicklers;~~ umgesetzt.

Die ausgefüllte Ausgangsfassung liegt in `PRIVACY_POLICY.md`; eine statische Webfassung
liegt unter `docs/`. Nach dem Push muss GitHub Pages noch für den veröffentlichten
Branch und den Ordner `/docs` aktiviert, die HTTPS-Adresse geprüft und anschließend in
der Play Console eingetragen werden.

Sobald später Werbung, Crash Reporting, Analytics oder Billing ergänzt werden, muss
die Datenerklärung erneut vollständig geprüft werden.

### 3. Altersfreigabe und App-Inhalte

In der Play Console müssen mindestens ausgefüllt werden:

- Fragebogen zur Altersfreigabe;
- Erklärung, ob Werbung enthalten ist (aktuell: nein);
- Zielgruppen- und Familienangaben;
- App-Zugriff (aktuell ist keine Anmeldung erforderlich);
- Angaben zu Nachrichten-, Gesundheits-, Finanz- oder sonstigen regulierten Inhalten,
  soweit von der Play Console abgefragt.

### 4. Monetarisierung

Digitale Zusatzinhalte müssen grundsätzlich über
[Google Play Billing](https://support.google.com/googleplay/android-developer/answer/10281818)
angeboten werden. Vor einer Integration fehlen noch:

- ein Elternbereich mit einer für Kinder nicht einfach lösbaren Elternschranke;
- klare Kennzeichnung von Preis, Leistungsumfang und Kaufstatus;
- eine Funktion **Käufe wiederherstellen**;
- Behandlung abgebrochener, ausstehender und stornierter Käufe;
- serverseitige Kaufprüfung oder eine begründete sichere Alternative;
- Aktualisierung von Datenschutz- und Datensicherheitsangaben.

Empfehlung für den ersten Store-Test: zunächst komplett kostenlos, ohne Werbung und
ohne In-App-Käufe veröffentlichen. Billing erst nach einem geschlossenen Test mit dem
Elternbereich ergänzen.

## Technische Play-Store-Anforderungen

- Vor dem Upload die jeweils gültige
  [Target-API-Anforderung](https://developer.android.com/google/play/requirements/target-sdk)
  prüfen. Das Projekt verwendet derzeit `targetSdk = 35` und `compileSdk = 35`; die
  Play Console entscheidet anhand von Upload-Datum und Veröffentlichungsart, ob eine
  neuere API erforderlich ist.
- Einen signierten Release-Build als `.aab` erzeugen, nicht nur eine Debug-APK.
- `versionCode` für jeden Upload erhöhen und `versionName` sinnvoll pflegen.
- Einen eindeutigen, dauerhaft beibehaltenen `applicationId` verwenden. Eine nach der
  Veröffentlichung geänderte App-ID wird von Google Play als neue App behandelt.
- Pre-Launch-Report, automatisierte Gerätetests und Warnungen der Play Console ohne
  kritische Fehler abschließen.

## Noch fehlende Store- und Rechtsinhalte

- endgültiger App-Name und eindeutige Paket-/Markenprüfung;
- hochauflösendes App-Symbol und adaptives Launcher-Icon;
- Feature-Grafik sowie echte Smartphone-/Tablet-Screenshots;
- kurze und vollständige Store-Beschreibung;
- Support-E-Mail und optional Support-Webseite;
- öffentlich erreichbare Datenschutzerklärung;
- Anbieterkennzeichnung/Impressum entsprechend Land und Vertriebsmodell;
- Inhaltsbewertung, Zielgruppe, Datensicherheit und Werbeerklärung;
- geschlossener Test mit Eltern und Kindern unter angemessener Aufsicht;
- Prüfung, ob für das verwendete Google-Play-Entwicklerkonto die aktuell verlangte
  Testdauer bzw. Mindestanzahl an Testern gilt.

## Empfohlene Release-Reihenfolge

1. ~~Datenschutz-/Eltern-Info innerhalb der App ergänzen.~~ Grundversion und echte
   Kontaktdaten umgesetzt; vor Release noch die öffentliche Datenschutz-URL einsetzen.
2. Datenschutzerklärung und Supportseite unter HTTPS veröffentlichen.
3. Adaptives App-Icon, Store-Texte und Store-Grafiken erstellen.
4. Release-AAB mit sicherem Upload-Schlüssel erzeugen.
5. Internen Test und anschließend geschlossenen Test durchführen.
6. Pre-Launch-Report, Richtlinienformulare und Datensicherheit korrigieren.
7. Erst danach Produktionsfreigabe beantragen.
8. Elternbereich und Play Billing in einer späteren, separat getesteten Version ergänzen.
