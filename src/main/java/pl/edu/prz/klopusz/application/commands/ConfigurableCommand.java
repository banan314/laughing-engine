package pl.edu.prz.klopusz.application.commands;

import pl.edu.prz.klopusz.application.model.ConfigurationModel;

/**
 * Created by kamil on 26.06.17.
 */
public interface ConfigurableCommand extends Command {
    public void configure(ConfigurationModel configurationModel);
}
