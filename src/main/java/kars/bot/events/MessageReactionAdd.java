package kars.bot.events;

import kars.bot.Console;
import kars.bot.DiscordBot;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MessageReactionAdd extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        super.onMessageReactionAdd(event);

        boolean positive;
        Message current = event.getChannel().retrieveMessageById(event.getMessageId()).complete();

        if (DiscordBot.chat != null) {
            switch (event.getReaction().getEmoji().getName()) {
                case "👍" -> {
                    positive = true;
                }
                case "👎" -> {
                    positive = false;
                }
                default -> {
                    return;
                }
            }
            DiscordBot.chat.brain.feedback(
                    current.getContentRaw(),
                    DiscordBot.chat.brain.getPrompt(),
                    positive
            );
        }
    }
}
