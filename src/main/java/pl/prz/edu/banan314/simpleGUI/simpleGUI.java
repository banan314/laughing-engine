package pl.prz.edu.banan314.simpleGUI;

/**
 * Created by kamil on 15.05.17.
 */

import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.prz.edu.banan314.simpleGUI.dolar.DolarCanvas;

import javax.swing.*;
import org.ggp.base.apps.kiosk.GameCanvas;
import pl.prz.edu.banan314.simpleGUI.dolar.GameGUI;

public class simpleGUI extends Application {

    @Override
    public void start(Stage stage) {
        final SwingNode swingNode = new SwingNode();
        createAndSetSwingContent(swingNode);

        Pane pane = new Pane();
        pane.getChildren().add(swingNode); // Adding swing node

        stage.setScene(new Scene(pane));
        stage.setHeight(600);
        stage.setWidth(1000);
        stage.show();
    }

    private void createAndSetSwingContent(final SwingNode swingNode) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameCanvas dolarCanvas = new DolarCanvas();
                JPanel panel = new GameGUI(dolarCanvas);
                panel.add(new JButton("Click me!"));
                swingNode.setContent(panel);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
