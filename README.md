Welcome to *Dolar* game project! 

See documentation on https://github.com/banan314/dolar-docs. 

It uses GENERAL GAME PLAYING BASE PACKAGE, see https://github.com/ggp-org/ggp-base. Its package is `org.ggp.base`.

The author's classes are inside package `pl.edu.prz.klopusz`. The JavaFX application is built inside `pl.edu.prz.klopusz.application`.

## Download and run

Releases can be downloaded from https://github.com/banan314/laughing-engine/releases. 
After you download the jar file, you can run it using 
```
java -jar dolar.jar
```
from the directory you uncompressed it to.
You have to have JRE 1.8+.

## GGP Theory

StateMachineGamer is based on the state machine view
of general game playing, in which playing a game is represented as proceeding
through a state machine. The underlying state machine, which you can access via
getStateMachine() when inheriting from StateMachineGamer, provides methods that
you can use to investigate the game being played:

    * Each game has a starting state.
        getInitialState() is the starting state.

    * Each state has legal moves for every player.
        getLegalMoves(state, role) are the legal moves for <role> in <state>.

    * Some states are terminal, and in those states "goal" values are defined
      for every player, indicating whether they won or lost.
        isTerminal(state) indicates whether a state is terminal.
        getGoal(state, role) is the goal value for <role> in <state>.

    * Given a legal move for each player, you can transition from one state to
      the next state, after the players make their respective moves.
        getNextState(state, moves) is the result of making <moves> at <state>.

