package kars.bot.events;

import kars.bot.Console;
import kars.bot.DiscordBot;
import kars.bot.embeds.AnnounceEmbed;
import kars.bot.embeds.HelpEmbed;
import kars.bot.embeds.InfoEmbed;
import kars.bot.embeds.ScoreEmbed;
import kars.bot.games.RockPaperScissors;
import kars.bot.games.Slots;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
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
            case "scores" -> {
                ScoreEmbed embed = new ScoreEmbed(DiscordBot.scoresLogger.readAll());
                event.replyEmbeds(embed).queue();
            }
            case "announce" -> {
                OptionMapping announceOption = event.getOption("announcement");
                OptionMapping titleOption = event.getOption("title");
                if (announceOption != null && titleOption != null) {
                    String announcement = announceOption.getAsString();
                    String title = titleOption.getAsString();
                    if (event.getMember() == event.getGuild().getOwner()) {
                        AnnounceEmbed embed = new AnnounceEmbed(title, announcement);
                        event.replyEmbeds(embed).queue();
                    }
                }
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

                switch (choice) {
                    case "Rock", "rock", "Paper", "paper", "Scissors", "scissors" -> {
                        DiscordBot.rps = new RockPaperScissors(event, event.getUser(), oppOption.getAsUser(), choice);
                    }
                    default -> {
                        event.reply("Invalid choice, try again").queue();
                    }
                }
            }
            case "slots" -> {
                OptionMapping betMapping = event.getOption("bet");
                double balance;
                try {
                    balance = DiscordBot.balanceLogger.loadValue(event.getUser().getEffectiveName());
                    if (balance < 5) {
                        balance = 50;
                    }
                    DiscordBot.balanceLogger.saveValue(event.getUser(), balance, "slots");
                }
                catch (Exception e) {
                    Console.debug("User was not found in balance.json");
                    DiscordBot.balanceLogger.saveValue(event.getUser(), 100, "slots");
                    balance = 100;
                }
                Console.debug(String.valueOf(balance));

                double bet;
                if (betMapping != null) {
                    bet = betMapping.getAsDouble();
                    if (bet > balance) {
                        event.reply("You don't have enough balance").queue();
                        return;
                    }
                }
                else {
                    bet = 10.0;
                }
                DiscordBot.slots = new Slots(event, event.getUser(), balance, bet);
            }
            case "balance" -> {
                double balance;
                try {
                    balance = DiscordBot.balanceLogger.loadValue(event.getUser().getEffectiveName());
                    if (balance < 5) {
                        balance = 50;
                    }
                    DiscordBot.balanceLogger.saveValue(event.getUser(), balance, "slots");
                }
                catch (Exception e) {
                    Console.debug("User was not found in balance.json");
                    DiscordBot.balanceLogger.saveValue(event.getUser(), 100, "slots");
                    balance = 100;
                }
                event.reply("Your balance : " + balance).queue();
            }
            case "balanceranking" -> {
                ScoreEmbed embed = new ScoreEmbed(DiscordBot.balanceLogger.readAll());
                event.replyEmbeds(embed).queue();
            }
        }
    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("hi", "Say hi"));
        commandData.add(Commands.slash("info", "Info about the bot"));
        commandData.add(Commands.slash("help", "Help information"));
        commandData.add(Commands.slash("scores", "Get scores"));
        commandData.add(Commands.slash("balance", "Check your balance"));
        commandData.add(Commands.slash("balanceranking", "Check everyones balance"));

        OptionData oppOption = new OptionData(OptionType.USER, "opponent", "pick your opponent", true, false);
        OptionData rpsOption = new OptionData(OptionType.STRING, "choice", "rock/paper/scissors", true, false);
        commandData.add(Commands.slash("rps", "Rock Paper Scissors").addOptions(oppOption, rpsOption));

        OptionData betOption = new OptionData(OptionType.STRING, "bet", "type amount you want to bet", true, false);
        commandData.add(Commands.slash("slots", "Spin a slot").addOptions(betOption));

        OptionData typOption = new OptionData(OptionType.STRING, "title", "title for announcement", true, false);
        OptionData annOption = new OptionData(OptionType.STRING, "announcement", "give announcement", true, false);
        commandData.add(Commands.slash("announce", "Announce message").addOptions(typOption, annOption));

        event.getGuild().updateCommands().addCommands(commandData).queue();
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {

    }
}