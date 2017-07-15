package pl.edu.prz.klopusz.application.commands.impl.player;

/**
 * Created by kamil on 15.07.17.
 */
public class StopPlayersCommand extends PlayerCommand {
    @Override
    public void execute() {
        CreatePlayerCommand.players.forEach((port, player) -> {
            player.shutdown();
        });
        CreatePlayerCommand.playing.clear();
        CreatePlayerCommand.players.clear();
    }
}
