package pl.prz.edu.banan314.application.creators;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import pl.prz.edu.banan314.application.DolarMainApp;
import pl.prz.edu.banan314.application.commands.impl.NewServerCommand;
import pl.prz.edu.banan314.application.model.BoardModel;

import java.net.URL;

/**
 * Created by kamil on 14.06.17.
 */
public class BoardCreator {
    final private static String BASE_VIEW_URL = DolarMainApp.BASE_VIEW_URL;

    BoardModel boardModel = new BoardModel();

    public void showBoard(BorderPane overview) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL(BASE_VIEW_URL+"board/Board.fxml"));

        AnchorPane boardOverview = loader.load();
        overview.setCenter(boardOverview);
    }

    public void bindModel() {

    }

    public void prepareGame() {
        NewServerCommand newServerCommand = new NewServerCommand();
        newServerCommand.execute();
    }
}
