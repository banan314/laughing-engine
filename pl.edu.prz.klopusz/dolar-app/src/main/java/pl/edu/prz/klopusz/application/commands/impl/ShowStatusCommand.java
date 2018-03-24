package pl.edu.prz.klopusz.application.commands.impl;

import pl.edu.prz.klopusz.application.commands.Command;
import pl.edu.prz.klopusz.application.controllers.RootLayout;

/**
 * Created by kamil on 01.07.17.
 */
public abstract class ShowStatusCommand implements Command {
    protected static RootLayout rootLayout;
    protected final String status;

    ShowStatusCommand(String status) {
        this.status = status;
    }

    public static void setRootLayout(RootLayout rootLayout) {
        ShowStatusCommand.rootLayout = rootLayout;
    }
}
