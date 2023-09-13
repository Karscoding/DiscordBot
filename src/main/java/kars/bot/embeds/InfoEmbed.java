package kars.bot.embeds;

import net.dv8tion.jda.api.entities.MessageEmbed;

public class InfoEmbed extends MessageEmbed {
    public InfoEmbed() {
        super(
                "",
                "Information",
                "This bot was developed by Karscoding using Java Discord API (JDA)\n for more information check out the github! https://github.com/Karscoding/DiscordBot",
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
