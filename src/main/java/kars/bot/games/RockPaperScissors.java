package kars.bot.games;

import kars.bot.Console;
import kars.bot.DiscordBot;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class RockPaperScissors extends Game {
    MessageReceivedEvent msgEvent;

    User winner;

    User user2;

    boolean solo;

    String input;
    String input2;

    String[] options = {"Rock", "Paper", "Scissors"};
    Random cpuChoice = new Random();

    public boolean awaitingInput;

    public RockPaperScissors(@NotNull SlashCommandInteractionEvent event, User userCaller, User opponent, String input) {
        super(event, userCaller);
        setName("rps");

        this.user2 = opponent;
        this.input = input;

        if (this.user2 == event.getJDA().getSelfUser()) {
            this.solo = true;
            this.input2 = options[cpuChoice.nextInt(options.length)];
            start();
        }
        else {
            this.awaitingInput = true;
            if (user1 == user2) {
                event.reply(userCaller.getEffectiveName() + " has challenged himself??, Good luck I guess....?").queue();
            }
            else {
                event.reply(userCaller.getEffectiveName() + " has challenged " +
                        opponent.getEffectiveName() + " to Rock Paper Scissors!\n" +
                        userCaller.getEffectiveName() + " has decided his move now we wait for " +
                        opponent.getEffectiveName() + " to make his choice!").queue();
            }
        }
    }

    public void giveInput2(String input, MessageReceivedEvent event) {
        this.input2 = input;
        if (this.input != null && this.input2 != null) {
            this.msgEvent = event;
            start();
        }
    }

    public void start() {
        Console.debug("RockPaperScissors.start() called with args: " + input + ", " + input2);

        if (input.equalsIgnoreCase(input2)) {
            if (solo) {
                event.reply("Tie Game!").queue();
            }
            else {
                msgEvent.getChannel().sendMessage("Tie Game!").queue();
            }
            return;
        }

        winner = checkWinner(input, input2);

        announceWinner(winner);
    }

    public User checkWinner(String input, String input2) {
        if (!input.equalsIgnoreCase(input2)) {
            if (input.equalsIgnoreCase("Rock")) {
                return input2.equalsIgnoreCase("Scissors") ? user1 : user2;
            }
            else if (input.equalsIgnoreCase("Scissors")) {
                return input2.equalsIgnoreCase("Paper") ? user1 : user2;
            }
            else if (input.equalsIgnoreCase("Paper")) {
                return input2.equalsIgnoreCase("Rock") ? user1 : user2;
            }
        }
        return null;
    }

    public void announceWinner(@NotNull User winner) {
        if (this.solo) {
            event.reply(
                    user1.getEffectiveName() + " Picked: " + input + "\n\n" +
                            user2.getEffectiveName() + " Picked: " + input2 + "\n\n" +
                            winner.getEffectiveName() + " Has Won!").queue();
        }
        else {
            msgEvent.getChannel().sendMessage(
                    user1.getEffectiveName() + " Picked: " + input + "\n\n" +
                        user2.getEffectiveName() + " Picked: " + input2 + "\n\n" +
                        winner.getEffectiveName() + " Has Won!").queue();
        }

        if(user1 != user2) {
            DiscordBot.scoresLogger.saveValue(winner, 1);
        }
    }

    public boolean checkOpponent(User user) {
        return this.user2.equals(user);
    }
}
