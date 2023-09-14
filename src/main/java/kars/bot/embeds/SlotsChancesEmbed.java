package kars.bot.embeds;

import net.dv8tion.jda.api.entities.MessageEmbed;

public class SlotsChancesEmbed extends MessageEmbed {
    public SlotsChancesEmbed() {
        super(
                "",
                " - Slots odds - ",
                "The chance in percentage to get a multiplier\n\n" +
                "50%  - 0.15x\n" +
                "25%  - 0.25x\n" +
                "15%  - 0.5x\n" +
                "5%   - 1x\n" +
                "2.5% - 5x\n" +
                "1%   - 25x\n" +
                "0.8% - 75\n" +
                "0.4% - 100x\n" +
                "0.2% - 200x\n" +
                "0.1% - 1500x\n",
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
    }}
