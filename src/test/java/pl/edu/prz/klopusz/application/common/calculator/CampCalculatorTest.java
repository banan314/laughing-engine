package pl.edu.prz.klopusz.application.common.calculator;

import javafx.stage.FileChooser;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import pl.edu.prz.klopusz.application.model.game.Board;
import pl.edu.prz.klopusz.application.model.game.SavedState;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.Assert.*;

public class CampCalculatorTest {
    private Board board;
    private CampCalculator campCalculator;

    @Before
    public void setUp() throws Exception {
        File file = new File("./pdn/wrongCamp.pdn");

        try {
            Scanner scanner = new Scanner(file);
            board = SavedState.loadBoard(scanner.nextLine());
        } catch (FileNotFoundException e) {
            throw new Exception("file " + file.getAbsolutePath() + " not found");
        }
        campCalculator = new CampCalculator(board);

        campCalculator.calculate();
    }

    @Test
    public void getWhite() throws Exception {
        assertEquals(3, campCalculator.getWhite());
    }

    @Test
    public void getBlack() throws Exception {
        assertEquals(3, campCalculator.getBlack());
    }

}