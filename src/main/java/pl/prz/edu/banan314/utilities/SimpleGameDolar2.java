package pl.prz.edu.banan314.utilities;

import org.ggp.base.util.game.Game;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

/**
 * Created by kamil on 11.06.17.
 */
public abstract class SimpleGameDolar2 {
    public static final String DOLAR_KIF_PATH = "./games/games/dolar/dolar.kif";

    protected static Game createDolarGame() {
        File dolarKif = fetchKif();

        String rulesheet = extractRuleSheet(dolarKif);
        return Game.createEphemeralGame(rulesheet);
    }

    static File fetchKif() {
        return new File(DOLAR_KIF_PATH);
    }

    static String extractRuleSheet(File dolarKif) {
        String rulesheet = null;
        rulesheet = readKif(dolarKif).toString();
        rulesheet = cleanRuleSheet(rulesheet);
        return rulesheet;
    }

    static StringBuffer readKif(File dolarKif) {
        StringBuffer sb = new StringBuffer();
        List<String> lines = null;
        try {
            lines = Files.readAllLines(dolarKif.toPath(), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(String line : lines) {
            sb.append(line).append('\n');
        }
        return sb;
    }

    private static String cleanRuleSheet(String rulesheet) {
        rulesheet = Game.preprocessRulesheet(rulesheet);
        return rulesheet;
    }
}
