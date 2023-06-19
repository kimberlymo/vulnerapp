# Vulnerapp

-- A Vulnerable Sample Spring Boot Application

This application uses a relatively modern stack but is still vulernable to a set of attacks.
Featuring:

- [XSS](https://portswigger.net/web-security/cross-site-scripting)
- [SQLi](https://portswigger.net/web-security/sql-injection)
- [CSRF](https://portswigger.net/web-security/csrf)
- [SSRF](https://portswigger.net/web-security/ssrf)
- Fake Logins
- Info Exposure
- Plain Passwords
- ...

```console
./gradlew bootRun
```

Beschreibung

Die Applikation enthält verschiedene Sicherheitslücken, die behoben werden müssen. Das grundlegende Verhalten der Applikation soll erhalten bleiben. Ziel der Aufgabe ist es, die Sicherheit der Applikation durch die Implementierung von gängigen Sicherheitsmassnahmen zu verbessern. Die grundlegenden Anforderungen umfassen:

- Verwendung von korrekten REST-Verben. ✔️
- Implementierung einer Authentifizierungslösung (z.B. BasicAuth). ✔️
- Enforce RBAC (z.B. User- und Admin-Services unterscheiden). ✔️
- Aktivieren von CSRF-Protection und Erklärung, warum diese Implementation funktioniert.
- Implementierung einer sicheren Passwort-Speicherung (Hashing, Passwortregeln). ✔️
- Strikte Inputvalidierung (für REST-Endpunkte und Datenbank). ✔️
- Behebung der initialen Sicherheitslücken (SQLi, XSS, CSRF). 
- Implementierung von securityrelevanten (Unit-)Tests. ✔️

Zusätzlich zu den grundlegenden Anforderungen können weitere Anpassungen vorgenommen werden, z.B. sicherheitsrelevante HTTP-Headers wie `content-security-policy` (CSP) zu setzen
, ein Limit auf fehlerhaften Loginversuchen um Brute-Force zu verhindern
, oder weniger information-disclosure via Fehlermedungen.

Etwas komplexere Erweitereungen könnte die Implementierung von OIDC-Authentifizierung gegenüber GitHub OAuth2, die Einrichtung eines automatisierten OWASP- oder CVE-Scanners oder die Aktivierung von HTTPS mit einem Self-Signed Zertifikat, sein.

Diskussion und Selbstevaluation

- Diskutieren Sie, welche Sicherheitsmechanismen wo implementiert wurden und warum diese die Sicherheit der Applikation verbessern.
- Diskutieren Sie weitere mögliche und sinnvolle Sicherheitsmechanismen, die implementiert werden könnten.
- Reflektieren Sie über potenzielle Schwierigkeiten oder Probleme bei der Implementierung und was anders gemacht werden könnte.

Es wird empfohlen hierfür ChatGPT zu nutzen. Achte aber darauf, dass die Selbstevaluation dennoch persönlich ist, die Diskussion exakt auf ihre Applikation passt, und alle generierten Aussagen korrekt sind.

Abgabe als Git-Repo.