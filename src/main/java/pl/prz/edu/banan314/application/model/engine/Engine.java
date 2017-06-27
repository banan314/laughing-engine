package pl.prz.edu.banan314.application.model.engine;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;

/**
 * Created by kamil on 24.06.17.
 */
public enum Engine {
    RANDOM_ENGINE,
    NOOP_ENGINE,
    MONTE_CARLO_ENGINE;

    @Override
    public String toString() {
        switch (this) {
            case RANDOM_ENGINE: return "RandomGamer";
            case NOOP_ENGINE: return "SampleNoopGamer";
            case MONTE_CARLO_ENGINE: return "SampleMonteCarloGamer";
        }
        return "undefined";
    }

    public String className() {
        return toString();
    }

    public static ObservableList<Engine> observableList() {
        return FXCollections.observableList(
                Arrays.asList(RANDOM_ENGINE, NOOP_ENGINE, MONTE_CARLO_ENGINE)
        );
    }
}
