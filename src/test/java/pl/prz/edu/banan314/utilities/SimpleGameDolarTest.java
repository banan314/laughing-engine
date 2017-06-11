package pl.prz.edu.banan314.utilities;

import org.ggp.base.util.game.Game;
import org.ggp.base.util.statemachine.Role;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by kamil on 11.05.17.
 */
public class SimpleGameDolarTest {

    @Test
    public void fetchKif() throws Exception {
        File dolarKif = SimpleServerGameDolar.fetchKif();
        assertTrue(dolarKif.exists());
        assertTrue(dolarKif.canRead());
    }

    @Test
    public void readKif() throws Exception {
        StringBuffer sb = SimpleServerGameDolar.readKif(SimpleServerGameDolar.fetchKif());
        assertNotNull(sb);
        assertNotSame(new String(""), sb.toString());
        assertSame(';', sb.charAt(3));
    }

    @Test
    public void testRoles() throws Exception{
        File dolarKif = SimpleServerGameDolar.fetchKif();
        String rulesheet = SimpleServerGameDolar.extractRuleSheet(dolarKif);
        final Game game = Game.createEphemeralGame(rulesheet);

        assertEquals(2, Role.computeRoles(game.getRules()).size());
    }
}