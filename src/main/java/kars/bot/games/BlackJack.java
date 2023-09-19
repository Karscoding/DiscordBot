package kars.bot.games;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;

public class BlackJack extends Game{
    public BlackJack(@NotNull SlashCommandInteractionEvent event, User player) {
        super(event, player);
        setName("bj");
    }

    @Override
    public void start() {

    }
}
