package pl.prz.edu.banan314.application;

/**
 * Created by kamil on 13.05.17.
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import pl.prz.edu.banan314.application.controllers.RootLayout;
import pl.prz.edu.banan314.application.creators.BoardCreator;

import java.util.Optional;

public class DolarMainApp extends Application {

    public static final String BASE_VIEW_URL = "file:./src/main/java/pl/prz/edu/banan314/application/view/";

    private BorderPane overview;
    private BoardCreator boardCreator = new BoardCreator();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Dolar");
        FXMLLoader loader = new FXMLLoader();
        try {
            java.net.URL location = new java.net.URL(BASE_VIEW_URL + "RootLayout.fxml");
            if (null == location) throw new Exception("not found");
            System.out.println(location);
            loader.setLocation(location);

            overview = loader.load();

            Scene scene = new Scene(overview);
            primaryStage.setScene(scene);
            primaryStage.show();

            boardCreator.showBoard(overview);
        } catch (Exception e) {
            e.printStackTrace();
        }

        boardCreator.prepareGame();
        RootLayout rootLayout = loader.getController();
        rootLayout.setBoardModel(boardCreator.getBoardModel());
    }
}
