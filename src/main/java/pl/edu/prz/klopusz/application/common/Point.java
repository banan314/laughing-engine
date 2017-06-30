package pl.edu.prz.klopusz.application.common;

/**
 * Created by kamil on 10.06.17.
 */
public class Point<T extends Number> {
    public Point(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public T x, y;
}
