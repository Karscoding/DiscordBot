package kars.bot.embeds;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.Nullable;

public class CommandsEmbed extends EmbedBuilder {
    public CommandsEmbed(@Nullable MessageReceivedEvent event) {
        this.setTitle(" - Commands - ");
        this.addField("?info", "Shows general information about the bot",false);
        this.addField("?help", "Help command", false);
        this.addField("?rps", "Rock Paper Scissors (Coming soon)", false);

        if (event != null) {
            this.setFooter("Developed by Karscoding", event.getGuild().getOwner().getUser().getAvatarUrl());
        }
    }
}
