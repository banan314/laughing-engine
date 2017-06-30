package pl.edu.prz.klopusz.application.creators;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import pl.edu.prz.klopusz.application.model.BoardModel;
import pl.edu.prz.klopusz.application.DolarMainApp;
import pl.edu.prz.klopusz.application.commands.impl.server.NewServerCommand;
import pl.edu.prz.klopusz.application.commands.impl.server.ServerCommand;
import pl.edu.prz.klopusz.application.controllers.Board;
import pl.edu.prz.klopusz.application.model.ConfigurationModel;
import pl.edu.prz.klopusz.application.model.observer.ServerObserverImpl;

import java.net.URL;

/**
 * Created by kamil on 14.06.17.
 * I make singleton because I don't see any other way
 */
public class BoardCreator {
    private static BoardCreator instance = null;

    protected BoardCreator() {
    }

    public static BoardCreator getInstance() {
        if(null == instance) {
            instance = new BoardCreator();
        }
        return instance;
    }

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

        fillServerTraits();
    }

    public synchronized void fillServerTraits() {
        ServerObserverImpl serverObserver = new ServerObserverImpl(ServerCommand.getMatch());
        serverObserver.setBoardModel(boardModel);
        ServerCommand.getGameServer().addObserver(serverObserver);
    }

    public Board getController() {
        return boardController;
    }
}
