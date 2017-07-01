package pl.edu.prz.klopusz.application.common;

/**
 * Created by kamil on 01.07.17.
 */
public class ThreadHelper {
    public static void sleep(int miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
