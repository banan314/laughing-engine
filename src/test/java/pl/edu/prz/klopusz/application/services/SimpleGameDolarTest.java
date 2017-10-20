package pl.edu.prz.klopusz.application.services;

import org.ggp.base.util.game.Game;
import org.ggp.base.util.statemachine.Role;
import org.junit.Test;
import pl.edu.prz.klopusz.application.services.DolarGameCreator;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by kamil on 11.05.17.
 */
public class SimpleGameDolarTest {

    @Test
    public void fetchKif() throws Exception {
        File dolarKif = DolarGameCreator.fetchKif();
        assertTrue(dolarKif.exists());
        assertTrue(dolarKif.canRead());
    }

    @Test
    public void readKif() throws Exception {
        StringBuffer sb = DolarGameCreator.readKif(DolarGameCreator.fetchKif());
        assertNotNull(sb);
        assertNotSame(new String(""), sb.toString());
        assertSame(';', sb.charAt(3));
    }

    @Test
    public void testRoles() throws Exception{
        File dolarKif = DolarGameCreator.fetchKif();
        String rulesheet = DolarGameCreator.extractRuleSheet(dolarKif);
        final Game game = Game.createEphemeralGame(rulesheet);

        assertEquals(2, Role.computeRoles(game.getRules()).size());
    }
}