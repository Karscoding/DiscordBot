package kars.bot.events;

import kars.bot.DiscordBot;
import kars.bot.embeds.CommandsEmbed;
import kars.bot.embeds.InfoEmbed;
import kars.bot.games.RockPaperScissors;
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
            if (msgContent.equals("?help")) {
                CommandsEmbed embed = new CommandsEmbed(event);
                msg.sendEmbed(embed);
                embed.clear();
            }

            if (msgContent.equals("?info")) {
                InfoEmbed embed = new InfoEmbed();
                msg.sendEmbed(embed);
                embed.clear();
            }

            if (msgContent.equals("?rps solo")) {
                msg.send("Give your choice (Rock, Paper, Scissors)");
                DiscordBot.rps = new RockPaperScissors(event, msg, true, caller);
            }

            if (msgContent.equals("?rps")) {
                msg.send("Ping your opponent");
                DiscordBot.rps = new RockPaperScissors(event, msg, false, caller);
            }

            if (msgContent.equals("?cancel")) {
                msg.send("Cancelling current game");
                DiscordBot.rps = null;
            }

            if (DiscordBot.rps != null) {
                if (DiscordBot.rps.awaitingInput) {
                    if (!DiscordBot.rps.solo) {
                        if (DiscordBot.rps.awaitingUser) {
                            if (event.getAuthor() == DiscordBot.rps.user1) {
                                User pingeduser = event.getMessage().getMentions().getUsers().get(0);
                                DiscordBot.rps.setOpponent(pingeduser);
                                msg.send("Awaiting " + pingeduser.getEffectiveName() + "'s input");
                                DiscordBot.rps.awaitingUser = false;
                            }
                        }
                    }

                    switch (msgContent) {
                        case "Rock", "Paper", "Scissors" -> {
                            DiscordBot.rps.giveInput(msgContent, event.getAuthor());
                        }
                    }
                }
            }
        }
    }
}
