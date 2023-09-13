package kars.bot.embeds;

import net.dv8tion.jda.api.entities.MessageEmbed;

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
