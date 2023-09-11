package kars.bot.embeds;

import net.dv8tion.jda.api.entities.EmbedType;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.time.OffsetDateTime;
import java.util.List;

public class ScoreEmbed extends MessageEmbed {
    public ScoreEmbed() {
        super(
                "",
                "Scores",
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
