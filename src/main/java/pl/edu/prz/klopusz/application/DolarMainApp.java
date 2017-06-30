package pl.edu.prz.klopusz.application;

/**
 * Created by kamil on 13.05.17.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.edu.prz.klopusz.application.controllers.Board;
import pl.edu.prz.klopusz.application.controllers.RootLayout;
import pl.edu.prz.klopusz.application.creators.BoardCreator;

public class DolarMainApp extends Application {

    public static final String BASE_VIEW_URL = "file:./src/main/java/pl/edu/prz/klopusz/application/view/";

    private BorderPane overview;
    private BoardCreator boardCreator = BoardCreator.getInstance();

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
        rootLayout.setParentApp(this);
    }

    public Board getBoardController() {
        return boardCreator.getController();
    }
}
