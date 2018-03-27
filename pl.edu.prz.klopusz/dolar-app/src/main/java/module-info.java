module pl.edu.prz.klopusz {
    requires org.ggp.base;

    provides org.ggp.base.util.statemachine.StateMachine
        with pl.edu.prz.klopusz.utilities.decorators.StateMachineDecorator;
}