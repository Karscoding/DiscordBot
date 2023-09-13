package kars.bot.games;

import net.dv8tion.jda.api.entities.User;
import org.jetbrains.annotations.NotNull;

public abstract class Game {
    private static String name;

    public String getName() {
        return name;
    }
    public void setName(String name) { Game.name = name; }

    public abstract void start();
}
