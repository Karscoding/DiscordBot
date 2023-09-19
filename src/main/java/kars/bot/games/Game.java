package kars.bot.games;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;

public abstract class Game {
    private static String name;
    public SlashCommandInteractionEvent event;
    public User user1;

    public Game(@NotNull SlashCommandInteractionEvent event, User player) {
        this.event = event;
        this.user1 = player;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { Game.name = name; }

    public abstract void start();
}
