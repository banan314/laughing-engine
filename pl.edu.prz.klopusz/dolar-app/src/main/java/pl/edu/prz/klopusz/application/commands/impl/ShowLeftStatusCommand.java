package pl.edu.prz.klopusz.application.commands.impl;

/**
 * Created by kamil on 01.07.17.
 */
public class ShowLeftStatusCommand extends ShowStatusCommand {
    public ShowLeftStatusCommand(String status) {
        super(status);
    }

    @Override
    public void execute() {
        rootLayout.showLeftStatus(status);
    }
}
