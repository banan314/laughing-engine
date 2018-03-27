module org.ggp.base {
    requires guava;
    requires reflections;
    requires jdk.httpserver;

    uses org.ggp.base.util.statemachine.StateMachine;
    exports org.ggp.base;
}