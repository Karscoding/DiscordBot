package kars.bot.embeds;

import net.dv8tion.jda.api.entities.EmbedType;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.time.OffsetDateTime;
import java.util.List;

public class AnnounceEmbed extends MessageEmbed {
    public AnnounceEmbed(String message) {
        super(null,
                " - NEW FEATURE - ",
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
