package pl.prz.edu.banan314.application.creators;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import pl.prz.edu.banan314.application.DolarMainApp;
import pl.prz.edu.banan314.application.commands.impl.server.NewServerCommand;
import pl.prz.edu.banan314.application.commands.impl.server.ServerCommand;
import pl.prz.edu.banan314.application.controllers.Board;
import pl.prz.edu.banan314.application.model.BoardModel;
import pl.prz.edu.banan314.application.model.ConfigurationModel;
import pl.prz.edu.banan314.application.model.observer.ServerObserverImpl;

import java.net.URL;

/**
 * Created by kamil on 14.06.17.
 */
public class BoardCreator {
    final private static String BASE_VIEW_URL = DolarMainApp.BASE_VIEW_URL;

    BoardModel boardModel = new BoardModel();
    ConfigurationModel configurationModel = new ConfigurationModel();
    Board boardController;

    public BoardModel getBoardModel() {
        return boardModel;
    }

    public void showBoard(BorderPane overview) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new URL(BASE_VIEW_URL+"board/Board.fxml"));

        AnchorPane boardOverview = loader.load();
        overview.setCenter(boardOverview);

        boardController = loader.getController();
    }

    private void bindModel() {
        assert boardController != null;
        boardController.setBoardModel(boardModel);
        boardModel.addObserver(boardController);

        boardController.setConfigurationModel(configurationModel);
    }

    public void prepareGame() {
        bindModel();

        NewServerCommand newServerCommand = new NewServerCommand();
        newServerCommand.execute();

        ServerObserverImpl serverObserver = new ServerObserverImpl(ServerCommand.getMatch());
        serverObserver.setBoardModel(boardModel);
        ServerCommand.getGameServer().addObserver(serverObserver);
    }

    public Board getController() {
        return boardController;
    }
}
