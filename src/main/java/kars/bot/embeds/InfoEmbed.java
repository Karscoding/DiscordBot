package kars.bot.embeds;

import net.dv8tion.jda.api.EmbedBuilder;

public class InfoEmbed extends EmbedBuilder {
    public InfoEmbed() {
        this.setTitle(" - Bot Information - ");
        this.setDescription("This bot was developed by Karscoding using Java Discord API");
    }
}
