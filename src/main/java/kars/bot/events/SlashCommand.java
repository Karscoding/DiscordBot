package kars.bot.events;

import kars.bot.DiscordBot;
import kars.bot.embeds.HelpEmbed;
import kars.bot.embeds.InfoEmbed;
import kars.bot.games.RockPaperScissors;
import kars.bot.logging.LogScores;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SlashCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case "hi" -> event.reply("Hi!").queue();
            case "info" -> {
                InfoEmbed embed = new InfoEmbed();
                event.replyEmbeds(embed).queue();
            }
            case "help" -> {
                HelpEmbed embed = new HelpEmbed();
                event.replyEmbeds(embed).queue();
            }
            case "say" -> {
                OptionMapping msgOption = event.getOption("message");
                if (msgOption != null) {
                    String message = msgOption.getAsString();
                    event.reply(message).queue();
                }
            }
            case "scores" -> {
                event.reply(LogScores.readScore()).queue();
            }
            case "rps" -> {
                OptionMapping rpsOption = event.getOption("choice");
                OptionMapping oppOption = event.getOption("opponent");
                if (rpsOption == null) {
                    break;
                }

                if (oppOption == null) {
                    break;
                }

                String choice = rpsOption.getAsString();

                switch (rpsOption.getAsString()) {
                    case "Rock", "rock", "Paper", "paper", "Scissor", "scissor" -> {
                        DiscordBot.rps = new RockPaperScissors(event, event.getUser(), oppOption.getAsUser(), choice);
                    }
                    default -> {
                        event.reply("Invalid choice, try again").queue();
                    }
                }
            }
        }
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("hi", "Say hi"));
        commandData.add(Commands.slash("info", "Info about the bot"));
        commandData.add(Commands.slash("help", "Help information"));
        commandData.add(Commands.slash("scores", "get scores"));

        OptionData oppOption = new OptionData(OptionType.USER, "opponent", "pick your opponent", true, false);
        OptionData rpsOption = new OptionData(OptionType.STRING, "choice", "rock/paper/scissors", true, false);
        commandData.add(Commands.slash("rps", "Rock Paper Scissors").addOptions(oppOption, rpsOption));

        OptionData option1 = new OptionData(OptionType.STRING, "message", "fill in message", true, false);
        commandData.add(Commands.slash("say", "Say message").addOptions(option1));

        event.getGuild().updateCommands().addCommands(commandData).queue();
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {

    }

}
