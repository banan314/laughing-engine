package pl.edu.prz.klopusz.application.commands.impl;

/**
 * Created by kamil on 01.07.17.
 */
public class ShowRightStatusCommand extends ShowStatusCommand {
    public ShowRightStatusCommand(String status) {
        super(status);
    }

    @Override
    public void execute() {
        rootLayout.showRightStatus(status);
    }
}
