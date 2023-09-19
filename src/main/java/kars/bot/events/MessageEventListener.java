package kars.bot.events;

import kars.bot.DiscordBot;
import kars.bot.chatbot.Brain;
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

            if (DiscordBot.rps != null && DiscordBot.rps.awaitingInput && DiscordBot.rps.checkOpponent(caller)) {
                switch (msgContent) {
                    case "Rock", "rock", "Paper", "paper", "Scissors", "scissors" -> {
                        DiscordBot.rps.giveInput2(msgContent, event);
                    }
                }
            }

            if (DiscordBot.chat != null && DiscordBot.chat.awaitingPrompt && DiscordBot.chat.checkUser(caller)) {
                DiscordBot.chat.brain = new Brain(event, msgContent);
            }
        }
    }
}
