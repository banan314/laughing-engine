package pl.prz.edu.banan314.application.commands;

import pl.prz.edu.banan314.application.model.ConfigurationModel;

/**
 * Created by kamil on 26.06.17.
 */
public interface ConfigurableCommand extends Command {
    public void configure(ConfigurationModel configurationModel);
}
