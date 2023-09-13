package kars.bot.embeds;

import net.dv8tion.jda.api.entities.EmbedType;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.time.OffsetDateTime;
import java.util.List;

public class ScoreEmbed extends MessageEmbed {
    public ScoreEmbed(String message) {
        super(
                "",
                " - Scores - ",
                message,
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
