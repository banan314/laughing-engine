package pl.edu.prz.klopusz.application.common.calculator;

import org.ggp.base.util.observer.Event;
import org.ggp.base.util.observer.Observer;
import pl.edu.prz.klopusz.application.model.BoardModel;
import pl.edu.prz.klopusz.utilities.decorators.ScoreCalculator;

public class PayoutCalculator implements Observer, ScoreCalculator {
    ScoreCalculator territoryCalculator;
    ScoreCalculator campCalculator;
    BoardModel boardModel;

    public PayoutCalculator calculateTerritoryUsing(ScoreCalculator territoryCalculator) {
        this.territoryCalculator = territoryCalculator;
        return this;
    }

    public PayoutCalculator calculateCampUsing(ScoreCalculator campCalculator) {
        this.campCalculator = campCalculator;
        return this;
    }

    public PayoutCalculator observeBoardModel(BoardModel model) {
        this.boardModel = model;
        model.addObserver(this);
        return this;
    }

    @Override
    public void observe(Event event) {
        //TODO
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        boardModel.removeObserver(this);
    }

    @Override
    public void calculate() {
        assert null != territoryCalculator;
        assert null != campCalculator;

        territoryCalculator.calculate();
        campCalculator.calculate();
    }

    @Override
    public int getWhite() {
        return territoryCalculator.getWhite() + campCalculator.getWhite();
    }

    @Override
    public int getBlack() {
        return territoryCalculator.getBlack() + campCalculator.getBlack();
    }
}
