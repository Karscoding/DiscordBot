package kars.bot.embeds;

import net.dv8tion.jda.api.entities.MessageEmbed;

public class AnnounceEmbed extends MessageEmbed {
    public AnnounceEmbed(String title, String message) {
        super(null,
                title,
                message,
                null,
                null,
                15844367,
                null,
                null,
                null,
                null,
                new EmbedFooter(),
                null,
                null);
    }
}
