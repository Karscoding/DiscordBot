package kars.bot.events;

import kars.bot.DiscordBot;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MessageEventListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        MessageHandler msg = new MessageHandler(event);
        User caller = event.getAuthor();

        String msgContent = event.getMessage().getContentDisplay();

        if (event.getAuthor().isBot()) {
            return;
        }
        else {
            if (msgContent.equals("cancel")) {
                msg.send("Cancelling current game");
                DiscordBot.rps = null;
            }

            if (DiscordBot.rps != null && DiscordBot.rps.awaitingInput && event.getAuthor() == DiscordBot.rps.user2) {
                switch (msgContent) {
                    case "Rock", "rock", "Paper", "paper", "Scissor", "scissor" -> {
                        DiscordBot.rps.giveInput2(msgContent, event);
                    }
                }
            }
        }
    }
}
