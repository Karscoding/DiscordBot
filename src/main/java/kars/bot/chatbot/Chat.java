package kars.bot.chatbot;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;

public class Chat {
    SlashCommandInteractionEvent event;
    User user;

    public Brain brain;

    public boolean awaitingPrompt;

    public Chat(@NotNull SlashCommandInteractionEvent event, User caller) {
        this.event = event;
        this.user = caller;

        start();

        this.awaitingPrompt = true;
    }

    public boolean checkUser(User user) {
        return this.user.equals(user);
    }

    public void start() {
        event.reply("Hi there " + user.getEffectiveName() + "!\nLet's have a conversation,\ngive me a prompt.").queue();
    }
}
