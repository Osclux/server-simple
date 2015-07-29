# server-simple
A simple OpenTok server in java. The server creates a number of default OpenTok sessions (chat rooms)
that are intended for our beta testers to use.

The idea is that we give a tester a session name (a chat room name) and the tester can then use that name for himself and his 
friends during the entire test period. 

The mobile client should first ask the user for a chat room name, then ask the server for the data for that 
chat room (session name) by making this call: http://localhost:4567/session/<name> where <name> is replaced by 
the chat room name entered by the user. The server returns the parameters needed to connect to the OpenTok session.

Default session names are:

* utsikten
* insikten
* inblicken
* utblicken
* vidden
* vyn
* vilan
* visionen
* synen
* periskopet
* test
* kristin
* oscar
* john
* johanb
* johanc




To compile and start the server: 
`./gradlew run` 
or
`.\gradlew.bat run`


To test the server in a browser:

http://localhost:4567/activesessions

Should return a json object with a list of all the default sessions.

http://localhost:4567/session/test

Should return data for the session (chat room) named test.

http://localhost:4567/

Should start a web-client that connects to the "test" chat room.
