package kars.bot.games;

import kars.bot.Console;
import kars.bot.logging.LogScores;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class RockPaperScissors extends Game {
    SlashCommandInteractionEvent event;
    MessageReceivedEvent msgEvent;

    User winner;

    public User user1;
    public User user2;

    boolean solo;

    String input;
    String input2;

    public String[] options = {"Rock", "Paper", "Scissors"};
    Random cpuChoice = new Random();

    public boolean awaitingInput;

    public RockPaperScissors(SlashCommandInteractionEvent event, User userCaller, User opponent, String input) {
        setName("rps");

        this.event = event;
        this.user1 = userCaller;
        this.user2 = opponent;
        this.input = input;

        if (this.user2 == event.getJDA().getSelfUser()) {
            this.solo = true;
            this.input2 = options[cpuChoice.nextInt(options.length)];
            start();
        }
        else {
            this.awaitingInput = true;
            event.reply(userCaller.getEffectiveName() + " has challenged " +
                    opponent.getEffectiveName() + " to Rock Paper Scissors!\n" +
                    userCaller.getEffectiveName() + " has decided his move now we wait for " +
                    opponent.getEffectiveName() + " to make his choice!").queue();
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
                    user1.getEffectiveName() + " Picked: " + input + "\n" +
                            user2.getEffectiveName() + " Picked: " + input2 + "\n" +
                            winner.getEffectiveName() + " Has Won!").queue();
        }
        else {
            msgEvent.getChannel().sendMessage(
                    user1.getEffectiveName() + " Picked: " + input + "\n" +
                            user2.getEffectiveName() + " Picked: " + input2 + "\n" +
                            winner.getEffectiveName() + " Has Won!").queue();
        }

        LogScores.saveValue(winner, 1);
    }
}
