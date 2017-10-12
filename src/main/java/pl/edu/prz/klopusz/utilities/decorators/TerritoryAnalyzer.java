package pl.edu.prz.klopusz.utilities.decorators;

import pl.edu.prz.klopusz.engine.api.Territory;

public interface TerritoryAnalyzer {
    void analyze();

    Territory getWhiteTerritory();
    Territory getBlackTerritory();
}
