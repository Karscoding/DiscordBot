package kars.bot.games;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;

public class CoinFlip extends Game {
    public CoinFlip(@NotNull SlashCommandInteractionEvent event, User player) {
        super(event, player);
        setName("coinflip");
    }

    @Override
    public void start() {

    }
}
