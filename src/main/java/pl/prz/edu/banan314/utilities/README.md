It runs a simple game simulator for Dolar.
To run:
```
gradle simpleGameDolar
```
in terminal and many lines of game states and moves taken description should follow.

See also:
* ggp.base.player.gamer.clojure.**ClojureGamerTest**
* ggp.base.player.gamer.python.**PythonGamerTest**

Task to run a player/gamer:
```
task playerRunner(type: JavaExec) {
    main = 'org.ggp.base.apps.player.PlayerRunner'
    
    doFirst {
        args = [port, gamer]
    }
}
```