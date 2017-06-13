package org.ggp.base.util.statemachine;

import org.ggp.base.util.gdl.factory.GdlFactory;
import org.ggp.base.util.gdl.factory.exceptions.GdlFormatException;
import org.ggp.base.util.gdl.grammar.GdlConstant;
import org.ggp.base.util.gdl.grammar.GdlProposition;
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
}
