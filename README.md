# Maze Game

Requires JDK 17 or newer. Run these commands from the project directory so the
image and map resources are on the classpath:

```sh
javac *.java
java GameServer
```

In two other terminals, run `java GameStarter`. Both players connect to the
server's host on port `9999` and select different characters. Use WASD to move
and E to interact.

When either player runs out of lives, the server restarts the current level for
both players.
