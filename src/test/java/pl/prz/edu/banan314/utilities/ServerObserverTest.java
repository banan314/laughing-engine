package pl.prz.edu.banan314.utilities;

import org.ggp.base.server.GameServer;
import org.ggp.base.server.event.ServerCompletedMatchEvent;
import org.ggp.base.server.event.ServerNewGameStateEvent;
import org.ggp.base.server.event.ServerNewMovesEvent;
import org.ggp.base.util.game.Game;
import org.ggp.base.util.match.Match;
import org.ggp.base.util.observer.Event;
import org.ggp.base.util.observer.Observer;
import org.ggp.base.util.statemachine.MachineState;
import org.ggp.base.util.statemachine.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import pl.prz.edu.banan314.application.model.game.Square;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by kamil on 21.06.17.
 */
class ServerObserverTest {
    GameServer theServer;

    @BeforeEach
    public void setUp() throws Exception {
        Game game = new SimpleServerGameDolar().createDolarGame();
        Match theMatch = new Match("simpleGameSim."+Match.getRandomString(5), -1, 0, 0, game, "");

        // Set up fake players to pretend to play the game
        List<String> fakeHosts = new ArrayList<String>();
        List<Integer> fakePorts = new ArrayList<Integer>();
        for(int i = 0; i < Role.computeRoles(game.getRules()).size(); i++) {
            fakeHosts.add("SamplePlayer"+i);
            fakePorts.add(9147+i);
        }

        // Set up a game server to play through the game, with all players playing randomly.
        theServer = new GameServer(theMatch, fakeHosts, fakePorts);
        for(int i = 0; i < fakeHosts.size(); i++) {
            theServer.makePlayerPlayRandomly(i);
        }

        theServer.start();
    }

    @AfterEach
    public void tearDown() throws Exception {
        theServer.join();
    }

    @Test
    public void newGameTest() throws Exception {
        final ServerObserver observer = new ServerObserver(theServer.getMatch());

        theServer.addObserver(new Observer() {
            @Override public void observe(Event event) {
                try{
                    if (event instanceof ServerNewGameStateEvent) {
                        ServerNewGameStateEvent serverEvent = (ServerNewGameStateEvent) event;
                        //observer.handleNewGame(serverEvent);

                        MachineState state = serverEvent.getState();
                        state.getContents().stream()
                                //.filter(sentence -> Square.isCell(sentence.toString()))
                                .forEach(sentence -> {
                                            assertTrue(sentence.toString() + " should be cell", Square.isCell(sentence.toString())
                                                    ||
                                                    isPassed(sentence.toString())
                                                    ||
                                                    isControl(sentence.toString())
                                                    ||
                                                    isStep(sentence.toString()));
                                        }
                                );
                        assertTrue("should be at least 1 sentence in machine state",
                                state.getContents().size() > 0);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean isStep(String sentence) {
        return sentence.matches("(.*step.*)");
    }

    private boolean isControl(String sentence) {
        return sentence.matches("(.*control.*)");
    }

    private boolean isPassed(String sentence) {
        return sentence.matches("(.*passed.*)");
    }

    @Test public void newMovesTest() throws Exception {
        final ServerObserver observer = new ServerObserver(theServer.getMatch());

        theServer.addObserver(new Observer() {
            @Override public void observe(Event event) {
                try{
                    if (event instanceof ServerNewMovesEvent) {
                        ServerNewMovesEvent serverEvent = (ServerNewMovesEvent) event;
                        observer.handleNewMoves(serverEvent);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Test public void completedMatchTest() throws Exception {
        final ServerObserver observer = new ServerObserver(theServer.getMatch());

        theServer.addObserver(new Observer() {
            @Override public void observe(Event event) {
                try{
                    if (event instanceof ServerCompletedMatchEvent) {
                        ServerCompletedMatchEvent serverEvent = (ServerCompletedMatchEvent) event;
                        observer.handleCompletedMatch(serverEvent);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}