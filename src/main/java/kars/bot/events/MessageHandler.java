package kars.bot.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class MessageHandler {
    MessageReceivedEvent event;
    public MessageHandler(MessageReceivedEvent event) {
        this.event = event;
    }

    public void send(String message) {
        this.event.getChannel().sendMessage(message).queue();
    }

    public void sendEmbed(EmbedBuilder embed) {
        this.event.getChannel().sendMessageEmbeds(embed.build()).queue();
    }

    public boolean checkMsg(String check) {
        return this.event.getMessage().getContentDisplay().equalsIgnoreCase(check);
    }
}
