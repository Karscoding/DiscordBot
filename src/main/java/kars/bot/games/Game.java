package kars.bot.games;

import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;

public abstract class Game {
    public static String name;

    public String getName() {
        return name;
    }

    public abstract void start();
    public abstract void announceWinner(@NotNull User winner);
}
