package pl.prz.edu.banan314.application.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import pl.prz.edu.banan314.application.model.engine.Engine;

/**
 * Created by kamil on 26.06.17.
 */
public class ConfigurationModel {
    private StringProperty whiteEngineProperty = new SimpleStringProperty();
    private StringProperty blackEngineProperty = new SimpleStringProperty();

    public StringProperty whiteEngineProperty() {
        return whiteEngineProperty;
    }

    public StringProperty blackEngineProperty() {
        return blackEngineProperty;
    }

    public String getWhiteEngineProperty() {
        return toEngineName(whiteEngineProperty.getValue());
    }

    public String getBlackEngineProperty() {
        return toEngineName(blackEngineProperty.get());
    }

    private String toEngineName(String choice) {
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
