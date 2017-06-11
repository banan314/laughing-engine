package pl.prz.edu.banan314.application;

/**
 * Created by kamil on 13.05.17.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import pl.prz.edu.banan314.application.controller.RootLayout;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class DolarMainApp extends Application {

    private BorderPane overview;
    final private String BASE_VIEW_URL = "file:./src/main/java/pl/prz/edu/banan314/application/view/";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Dolar");
        try {
            FXMLLoader loader = new FXMLLoader();
            java.net.URL location = new java.net.URL(BASE_VIEW_URL + "RootLayout.fxml");
            if (null == location) throw new Exception("not found");
            System.out.println(location);
            loader.setLocation(location);

            overview = loader.load();
            customizeLayout();

            Scene scene = new Scene(overview);
            primaryStage.setScene(scene);
            primaryStage.show();

            showBoard();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showBoard() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL(BASE_VIEW_URL + "Board.fxml"));

        AnchorPane boardOverview = loader.load();
        overview.setCenter(boardOverview);
    }

    private void customizeLayout() {
        Optional<Node> anchorPane = overview.getChildren().stream().findFirst();
        AnchorPane anchorPane1 = (AnchorPane) anchorPane.get();
        Label world = new Label();
        world.setFont(new Font(15));
        world.setText("World!");
        world.setLayoutX(20);
        world.setLayoutY(20);
        anchorPane1.getChildren().add(world);


    }
}
