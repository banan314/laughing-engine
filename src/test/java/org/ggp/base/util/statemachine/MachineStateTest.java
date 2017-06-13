package org.ggp.base.util.statemachine;

import org.ggp.base.util.gdl.grammar.GdlConstant;
import org.ggp.base.util.gdl.grammar.GdlProposition;
import org.ggp.base.util.gdl.grammar.GdlSentence;
import org.ggp.base.util.gdl.grammar.GdlTerm;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Created by kamil on 13.06.17.
 */
public class MachineStateTest {

    @Test
    public void getContents() throws Exception {
        Set<GdlSentence> sentences = new HashSet<>();
        sentences.add(new GdlProposition(
                new GdlConstant("cell 1 1")
        ));
        MachineState ms = new MachineState(sentences);

        String result = ms.getContents().toString();

        assertThat(result, containsString("cell 1 1"));
    }

}