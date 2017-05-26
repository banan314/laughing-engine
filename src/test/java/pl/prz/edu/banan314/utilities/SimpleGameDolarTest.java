package pl.prz.edu.banan314.utilities;

import org.ggp.base.util.game.Game;
import org.ggp.base.util.statemachine.Role;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by kamil on 11.05.17.
 */
public class SimpleGameDolarTest {

    @Test
    public void fetchKif() throws Exception {
        File dolarKif = SimpleGameDolar.fetchKif();
        assertTrue(dolarKif.exists());
        assertTrue(dolarKif.canRead());
    }

    @Test
    public void readKif() throws Exception {
        StringBuffer sb = SimpleGameDolar.readKif(SimpleGameDolar.fetchKif());
        assertNotNull(sb);
        assertNotSame(new String(""), sb.toString());
        assertSame(';', sb.charAt(3));
    }

    @Test
    public void testRoles() throws Exception{
        File dolarKif = SimpleGameDolar.fetchKif();
        String rulesheet = SimpleGameDolar.extractRuleSheet(dolarKif);
        final Game game = Game.createEphemeralGame(rulesheet);

        assertEquals(2, Role.computeRoles(game.getRules()).size());
    }
}