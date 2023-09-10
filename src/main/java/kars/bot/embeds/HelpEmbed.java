package kars.bot.embeds;

import net.dv8tion.jda.api.entities.MessageEmbed;

public class HelpEmbed extends MessageEmbed {
    public HelpEmbed() {
        super(
                null,
                "Help",
                "This bot uses Slash commands, type a / to see all of the commands and its uses",
                null,
                null,
                15844367,
                null,
                null,
                null,
                null,
                new kars.bot.embeds.EmbedFooter(),
                null,
                null);
    }
}
