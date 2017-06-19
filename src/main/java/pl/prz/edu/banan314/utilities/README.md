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

In `RandomGamer`, we can debug legal moves in `stateMachineSelectMove`, using
```java
System.out.println("Possible moves: ");
for(Move move : moves) {
    System.out.println(move.toString());
}
```

Now I know where state machine comes from. Its instance is hard-coded in `GameServer` constructor:
```java
stateMachine = new ProverStateMachine();
stateMachine.initialize(match.getGame().getRules());
currentState = stateMachine.getInitialState();
```