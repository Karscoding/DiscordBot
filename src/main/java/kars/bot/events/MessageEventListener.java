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

        if (event.getAuthor().isBot()) {
            return;
        }
        else {
            if (msg.checkMsg("?help")) {
                CommandsEmbed embed = new CommandsEmbed(event);
                msg.sendEmbed(embed);
                embed.clear();
            }

            if (msg.checkMsg("?info")) {
                InfoEmbed embed = new InfoEmbed();
                msg.sendEmbed(embed);
                embed.clear();
            }

            if (msg.checkMsg("?rps solo")) {
                msg.send("Give your choice (Rock, Paper, Scissors)");
                DiscordBot.rps = new RockPaperScissors(event, msg, true, caller);
            }

            if (msg.checkMsg("?rps")) {
                msg.send("Ping your opponent");
                DiscordBot.rps = new RockPaperScissors(event, msg, false, caller);
            }

            if (msg.checkMsg("?cancel")) {
                msg.send("Cancelling current game");
                DiscordBot.rps = null;
            }

            if (DiscordBot.rps != null) {
                if (DiscordBot.rps.awaitingInput) {
                    if (!DiscordBot.rps.solo) {
                        if (DiscordBot.rps.user2 == null) {
                            if (event.getAuthor() == DiscordBot.rps.user1) {
                                User pingeduser = event.getMessage().getMentions().getUsers().get(0);
                                DiscordBot.rps.setOpponent(pingeduser);
                                msg.send("Awaiting " + pingeduser.getEffectiveName() + "'s input");
                            }
                        }
                        if (msg.checkMsg("Rock")) {
                            DiscordBot.rps.giveInput("Rock", event.getAuthor());
                        }
                        if (msg.checkMsg("Scissors")) {
                            DiscordBot.rps.giveInput("Scissors", event.getAuthor());
                        }
                        if (msg.checkMsg("Paper")) {
                            DiscordBot.rps.giveInput("Paper", event.getAuthor());
                        }
                    }
                    else if (msg.checkMsg("Rock")) {
                        DiscordBot.rps.giveInput("Rock", event.getAuthor());
                    }
                    else if (msg.checkMsg("Scissors")) {
                        DiscordBot.rps.giveInput("Scissors", event.getAuthor());
                    }
                    else if (msg.checkMsg("Paper")) {
                        DiscordBot.rps.giveInput("Paper", event.getAuthor());
                    }
                }
            }
        }
    }
}
