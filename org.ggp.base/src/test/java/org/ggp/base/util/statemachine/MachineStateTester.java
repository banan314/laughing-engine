package org.ggp.base.util.statemachine;

import org.ggp.base.util.gdl.factory.GdlFactory;
import org.ggp.base.util.gdl.factory.exceptions.GdlFormatException;
import org.ggp.base.util.gdl.grammar.GdlSentence;
import org.ggp.base.util.symbol.factory.exceptions.SymbolFormatException;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by kamil on 13.06.17.
 */
public class MachineStateTester {

    public static Set<GdlSentence> generateSimpleSentence() throws GdlFormatException, SymbolFormatException {
        Set<GdlSentence> sentences = new HashSet<>();
        sentences.add(
                (GdlSentence)GdlFactory.create("(cell 1 1 white)")
        );
        return sentences;
    }

    public static Set<GdlSentence> generateInitialBoard() throws GdlFormatException, SymbolFormatException {
        Set<GdlSentence> sentences = new HashSet<>();

        fill2Initial(sentences);

        StringBuffer sb = new StringBuffer("(cell 0 0 empty)");

        fillEmpty(sentences, sb, 6);

        return sentences;
    }

    public static Set<GdlSentence> generateTrueInitialBoard() throws GdlFormatException, SymbolFormatException {
        Set<GdlSentence> sentences = new HashSet<>();

        fill2Initial(sentences);

        StringBuffer sb = new StringBuffer("(true (cell 0 0 empty))");

        fillEmpty(sentences, sb, 6+"(true ".length());

        return sentences;
    }

    static void fillEmpty(Set<GdlSentence> sentences, StringBuffer sb, int rowIndex) throws GdlFormatException, SymbolFormatException {

        for(char row = '1'; row <= '9'; row++) {
            for(char file = '1'; file <= '9'; file++) {
                if(row==file && (row=='1' || row=='9'))
                    continue;
                sb.setCharAt(rowIndex,  row);
                sb.setCharAt(rowIndex+2,  file);
                sentences.add(
                        (GdlSentence) GdlFactory.create(sb.toString())
                );
            }
        }
    }

    static void fill2Initial(Set<GdlSentence> sentences) throws GdlFormatException, SymbolFormatException {
        sentences.add(
                (GdlSentence) GdlFactory.create("(cell 1 1 white)")
        );
        sentences.add(
                (GdlSentence)GdlFactory.create("(cell 9 9 black)")
        );
    }
}
