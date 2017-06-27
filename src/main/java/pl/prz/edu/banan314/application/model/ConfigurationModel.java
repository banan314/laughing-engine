package pl.prz.edu.banan314.application.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Setter;
import pl.prz.edu.banan314.application.model.engine.Engine;

/**
 * Created by kamil on 26.06.17.
 */
public class ConfigurationModel {

    @Setter String whiteEngine, blackEngine;

    public String getWhiteEngineProperty() {
        return toEngineName(whiteEngine);
    }

    public String getBlackEngineProperty() {
        return toEngineName(blackEngine);
    }

    private String toEngineName(String choice) {
        if(null == choice)
            return Engine.RANDOM_ENGINE.toString();
        if(choice.toLowerCase().contains("noop"))
            return Engine.NOOP_ENGINE.toString();
        else if(choice.toLowerCase().contains("random"))
            return Engine.RANDOM_ENGINE.toString();
        else if(choice.toLowerCase().contains("monte") && choice.toLowerCase().contains("carlo"))
            return Engine.MONTE_CARLO_ENGINE.toString();
        else
            return Engine.RANDOM_ENGINE.toString();
    }
}
