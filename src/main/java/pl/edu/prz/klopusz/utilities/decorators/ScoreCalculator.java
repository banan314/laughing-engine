package pl.edu.prz.klopusz.utilities.decorators;

/**
 * Created by kamil on 19.06.17.
 */
public interface ScoreCalculator {
    void calculate();

    int getWhite();
    int getBlack();
}
