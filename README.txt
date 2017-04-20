####################################
######## Original Challenge  #######
####################################

- http://ccd-school.de/en/coding-dojo/architecture-katas/url-shortener/
- Extrawünsche:
  - Anmeldung und Registrierung am System
  - Admin kann alle Statistiken einsehen, Benutzer sieht nur eigene Statistiken

Zeitrahmen: 3 Stunden

#####################################################################################################################
####### Herangehensweise
#####################################################################################################################

Ich habe versucht, die Services anhand eines MVP zu implementieren. Dabei lag der Fokus erstmal eher auf dem
Shortener und dem Redirector, da ich hier am schnellsten Resultate produzieren konnte.

Auf Kommentare im Source-Code habe ich weitestgehend verzichtet und dafür nach dem CleanCode-Prinzip versucht,
die Klassen und Methoden eindeutig und so klein wie möglich zu halten, so dass diese selbsterklärend sind.

Nicht oder nur teilweise umgesetzt wurden aufgrund der zeitlichen Limitierung die folgenden Features:

  - Das Frontend ist aufgrund meines derzeitigen Fokus auf Backends erstmal nur ruimentär implementiert
    (es fehlt z.B. Validierung der Eingaben sowie die Fehlerbehandlung im Frontend)

  - Der UserService ist noch unvollständig und beinhaltet bisher keine Unit- oder Integrationtests.

  - Frontend vom UserService ist noch unvollständig

  - Statistiken werden bereits geschrieben aber noch nicht beim Benutzer angezeigt

  - Ein Adminbereich fehlt noch komplett

  - Eine Sammelseite, die die beiden Frontend-Teile (Shortener und Login/Registrierung) auf einer Seite verbindet
    fehlt noch.

#####################################################################################################################

Das Projekt besteht aus mehreren Teilen:

######################
# testo-repositories #
######################

Beinhaltet die Anbindung an die MongoDB, in der die Daten gehalten werden, sowie den Converter von einer Zahlen-ID
aus der Datenbank zu einer "Short-URL" und wieder zurück.

Dieses Modul ist das einzige Modul, das zwischen den anderen Services "geshared" wird, da diese alle auf die
Datenbank zugreifen müssen und hierdurch auch keine unnötige Abhängigkeit zwischen den einzelnen Services entsteht.
Das Modul ist daher als Library zu betrachten.

Man könnte die benötigten Datenzugriffe auch in jedem einzelnen Microservice einzeln implementieren um frei von
Abhängigkeiten zu sein. Dies führt allerdings zur Code-Duplizierung. Die Meinungen gehen hier auseinander, was das
beste Vorgehen ist und unter welchen Umständen ein Code-Sharing in Microservices "erlaubt" ist. Meist wird das
"Share Nothing"-Prinzip propagiert. Ich habe mich hier aufgrund der kurzen Umsetzungszeit dazu entschieden, diesen
Code als Library zu sharen.

####################
# testo-redirector #
####################

Beinhaltet den Spring Boot Microservice (Port 8090) für die Umleitung der ShortURLs auf die lange URL, wenn man die
ShortUrl aufruft. Hier habe ich Beispielhaft ein paar Integration- und Unit-Tests implementiert. Die Datenbank wird
dabei über "Fongo" in Verbindung mit nosqlunit gemockt.

Beispielrequest: http://localhost:8090/a

####################
# testo-urlservice #
####################

Beinhaltet den Spring Boot REST-Microservice (Port 8080), der die URLs verkürzt sowie eine index.html, die
beispielhaft über JQuery den Request an den Service sendet und dann im Erfolgsfall die ShortURL anzeigt.
Eine detaillierte Fehlerbehandlung wurde im Frontend aufgrund der kurzen verfügbaren Zeit erstmal nicht umgesetzt.

Shorten-Request (anonym)
------------------------
URL: http://localhost:8080/rest/shorten
Request: {"longUrl":"http://xxx.de"}
Response-Codes: 200 (OK)

Shorten-Request (nach Anmeldung)
--------------------------------
URL: http://localhost:8080/rest/shorten
Request: {"longUrl":"http://xxx.de", "token":"xxxx"}
Response-Codes: 200 (OK), 401 (Unauthorized)

#####################
# testo-userservice #
#####################

Beinhaltet den Spring Boot REST-Microservice (Port 8081), der die Registrierung und den Login am System zulässt
und ein Token zurück gibt, mit dem man beim Urlservice nutzerabhängig eine URL speichern kann.

Dieser Service wurde zuletzt begonnen und ist daher noch unvollständig.

Register-Request
----------------
URL: http://localhost:8081/rest/register
Request: {"email":"xxx@xxx.de", "password":"xxx"}
Response-Codes: 200 (OK), 409 (Conflict)

Login-Request
-------------
URL: http://localhost:8081/rest/login
Request: {"email":"xxx@xxx.de", "password":"xxx"}
Response-Codes: 200 (OK), 401 (Unauthorized)
Response: {"token":"xxxxx"}