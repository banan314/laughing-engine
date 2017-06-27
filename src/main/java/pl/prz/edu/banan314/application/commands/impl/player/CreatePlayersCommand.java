package pl.prz.edu.banan314.application.commands.impl.player;

import lombok.Data;
import pl.prz.edu.banan314.application.commands.ConfigurableCommand;
import pl.prz.edu.banan314.application.model.ConfigurationModel;

/**
 * Created by kamil on 26.06.17.
 */
public class CreatePlayersCommand extends ConfigurablePlayerCommand  {
    EngineConfiguration whiteEngine, blackEngine;

    @Override
    public void execute() {
        new CreatePlayerCommand(whiteEngine.port, whiteEngine.getName()).execute();
        new CreatePlayerCommand(blackEngine.port, blackEngine.getName()).execute();

        System.out.printf("white: %s\nblack: %s\n", whiteEngine.getName(), blackEngine.getName());
    }

    @Override
    public void configure(ConfigurationModel configurationModel) {
        whiteEngine = new EngineConfiguration(9147, configurationModel.getWhiteEngineProperty());
        blackEngine = new EngineConfiguration(9148, configurationModel.getBlackEngineProperty());
    }

    @Data
    protected class EngineConfiguration {
        final int port;
        final String name;
    }
}
