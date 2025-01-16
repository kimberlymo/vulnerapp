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

**Beschreibung**

Die Applikation enthält verschiedene Sicherheitslücken, die behoben werden müssen. Das grundlegende Verhalten der Applikation soll erhalten bleiben. Ziel der Aufgabe ist es, die Sicherheit der Applikation durch die Implementierung von gängigen Sicherheitsmassnahmen zu verbessern. Die grundlegenden Anforderungen umfassen:

- Verwendung von korrekten REST-Verben. ✔️
- Implementierung einer Authentifizierungslösung (z.B. BasicAuth). ✔️
- Enforce RBAC (z.B. User- und Admin-Services unterscheiden). ✔️
- Aktivieren von CSRF-Protection und Erklärung, warum diese Implementation funktioniert.
- Implementierung einer sicheren Passwort-Speicherung (Hashing, Passwortregeln). ✔️
- Strikte Inputvalidierung (für REST-Endpunkte und Datenbank). ✔️
- Behebung der initialen Sicherheitslücken (SQLi, XSS, CSRF). ✔️
- Implementierung von securityrelevanten (Unit-)Tests. ✔️

Zusätzlich zu den grundlegenden Anforderungen können weitere Anpassungen vorgenommen werden, z.B. sicherheitsrelevante HTTP-Headers wie `content-security-policy` (CSP) zu setzen
, ein Limit auf fehlerhaften Loginversuchen um Brute-Force zu verhindern ✔️
, oder weniger information-disclosure via Fehlermedungen.

Etwas komplexere Erweitereungen könnte die Implementierung von OIDC-Authentifizierung gegenüber GitHub OAuth2, die Einrichtung eines automatisierten OWASP- oder CVE-Scanners oder die Aktivierung von HTTPS mit einem Self-Signed Zertifikat, sein.

Diskussion und Selbstevaluation

- Diskutieren Sie, welche Sicherheitsmechanismen wo implementiert wurden und warum diese die Sicherheit der Applikation verbessern.
- Diskutieren Sie weitere mögliche und sinnvolle Sicherheitsmechanismen, die implementiert werden könnten.
- Reflektieren Sie über potenzielle Schwierigkeiten oder Probleme bei der Implementierung und was anders gemacht werden könnte.

Es wird empfohlen hierfür ChatGPT zu nutzen. Achte aber darauf, dass die Selbstevaluation dennoch persönlich ist, die Diskussion exakt auf ihre Applikation passt, und alle generierten Aussagen korrekt sind.

Abgabe als Git-Repo.

## Diskussion
### Sicherheitsmechanismen
#### CSRF
Cross-Site Request Forgery (CSRF) ist eine Angriffstechnik, bei der vorbereitete Links mit bösartigen Absichten an Benutzer gesendet werden. Zum Beispiel können solche Links in E-Mails oder HTML-Dateien enthalten sein, und wenn der Benutzer einmal darauf klickt, kann der Angreifer den Angriff ausführen. In der Vulnerapp wurde eine Lösung für CSRF implementiert, und zwar durch Verwendung eines CSRF-Tokens. Das CSRF-Token muss den gleichen Inhalt haben wie das Cookie. Die Implementierung dafür findet sich in der Klasse `SecurityConfiguration` auf der Code-Seite. Im Projekt gibt es auch eine HTML-Datei namens `csrf-test.html`, mit der man sehen und testen kann, wie die CSRF-Lösung funktioniert und welche Meldungen der Server erhält. Für den Test dieser Datei muss möglicherweise CORS im Browser deaktiviert werden.

#### XSS
Durch XSS kann ein schädliches Skript in die Webanwendung hinzugefügt werden. Sodass dies nicht mehr geschehen kann, wurde in der Klasse `SecurityConfiguration` eine Lösung dazu implementiert.

#### Password Hashing
Damit die Passwörter sicher gespeichert sind und nicht jede Person, die Zugriff auf die Datenbank hat, die Passwörter sehen kann, wurde ein PasswordHash hinzugefügt. Der PasswordEncoder wurde in der Klasse `SecurityConfiguration` hinzugefügt. Beim Login und Erstellung eines Benutzers wird dieser PasswordEncoder benutzt.

#### Authentifizierung
Durch die Authentifizierung wird identifiziert wer der Benutzer ist. Zusammen mit der Autorisierung kann nicht jede Person auf gewisse Ressourcen zugreifen. Nur Personen die ein Login / Benutzerkonto haben, können sich gewisse Sachen anschauen. Durch die Autorisierung wird später begrenzt auf was der Benutzer zugreifen darf. In diesem Projekt haben wir BasicAuth verwendet und die Konfiguration ist in der Klasse namens `SecurityConfiguraiton` zu finden.

#### Autorisierung
Mit der Autorisierung kann definiert werden welcher Benutzer auf welche Ressourcen zugreifen kann. Zum Beispiel kann ein normaler Benutzer kein Benutzer erstellen, aber ein Admin schon. Bei der Vulnerapp arbeiten wir mit Rollen auch als Role-Based Access Control bekannt.Für die Autorisierung wurde ein neues Attribut `ROLE` zum Benutzer hinzugefügt. Die Konfiguration welcher Benutzer auf was zugreifen kann, ist in allen `Services` enthalten.

#### Inputvalidierung
Je nach Entität wird der Input verschieden validiert. Zum Beispiel kann ein Name des Benutzers nicht länger sein als 30 Zeichen, muss aber mindestens 3 Zeichen lang sein. Die Inputvalidierung wurde in allen `datamodels` implementiert.

#### Begrenzung für fehlgeschlagene Anmeldeversuche
Sodass ein Hacker keine Brute-Force Anmeldeversuche durchführen kann, wurde ein Limit gesetzt. Sobald sich der Benutzer fünf Mal falsch einloggt, wird die IP-Adresse des Benutzers für 5 Stunden gesperrt. Die Konfiguration ist in den Klassen `SecurityConfiguration`, `AuthenticationFailureListener`, `LoginAttemptService` und `CustomAuthenticationFailureHandler`. Im Moment gibt es Probleme mit der Klasse `CustomAuthenticationFailureHandler`, diese wird nie aufgerufen. Für diese Implementierung folgte ich dieses Tutorial: https://www.baeldung.com/spring-security-block-brute-force-authentication-attempts

### weitere Implementierungsmöglichkeiten
- **Two-Factor Authorization**: Reduziert das Risiko von einloggen einer anderen Person, die nicht der Benutzer ist. Dies kann mit einer Verification E-Mail gelöst werden.
- **SSRF**: Benutzereingaben noch besser validieren
- **weniger information-disclosure**: Zum Beispiel die Informationen aus den Fehlermeldungen reduzieren. 

## Selbstevaluation
Das Ziel dieses Projekts bestand darin, das Wissen darüber zu erlangen, wie Hacker Angriffe durchführen können und wie wir als Entwickler dies verhindern können. Zunächst wollten wir sicherstellen, dass wir grundlegende Lösungen für CSRF und SQL-Injection implementieren und verstehen. Falls noch Zeit übrig war, sollten weitere Funktionen wie beispielsweise eine Begrenzung für fehlgeschlagene Anmeldeversuche oder die Möglichkeit, sich mit einem GitHub-Account anzumelden, hinzugefügt werden.

Durch dieses Projekt habe ich viel über Sicherheit und insbesondere Spring Security gelernt. Ich habe nun Kenntnis über die verschiedenen Sicherheitsausnahmen, die in einer Anwendung auftreten können, und weiß, wie ich einige davon lösen kann. Zusätzlich habe ich auch gelernt, wie Hacker ihre Angriffe durchführen und wie einfach dies je nach Angriff sein kann.

Im Verlauf dieses Projekts hatte ich gelegentlich Probleme mit Spring Boot, da es für mich anfangs wie Magie wirkte. Wenn ich eine Annotation vergessen hatte, funktionierte der gesamte Code nicht mehr. Dank der Tutorials konnte ich jedoch besser verstehen, wie die Implementierung funktioniert, und ich habe Dinge mehrmals ausprobiert (Trial and Error). Dadurch wurde es mir ermöglicht, die Themen besser zu verstehen und die Umsetzung zu verbessern, da mein ursprüngliches Wissen manchmal nicht ausreichend war.

Ein Problem konnte ich jedoch nicht lösen. Ich wollte gerne benutzerdefinierte Fehlermeldungen anzeigen lassen, wenn sich ein Benutzer nicht anmelden konnte. Leider habe ich keine Lösung dafür gefunden, und dies stellt sicherlich einen Verbesserungsbereich dar.

Zusammenfassend finde ich, dass ich die Themen gut verstanden habe, aber es gibt immer Raum für weitere Kenntnisse und Verbesserungen.
