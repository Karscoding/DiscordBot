package kars.bot.embeds;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class InfoEmbed extends MessageEmbed {
    public InfoEmbed() {
        super(
                "",
                "Information",
                "This bot was developed by Karscoding using Java Discord API (JDA)",
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
