package kars.bot.games;

import kars.bot.Console;
import kars.bot.events.MessageHandler;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class RockPaperScissors {
    SlashCommandInteractionEvent event;
    MessageReceivedEvent msgEvent;
    MessageHandler msg;
    User winner;

    public User user1;
    public User user2;

    boolean solo;

    String input;
    String input2;

    public String[] options = {"Rock", "Paper", "Scissors"};
    Random cpuChoice = new Random();

    public boolean awaitingInput;
    public boolean awaitingUser;

    public RockPaperScissors(SlashCommandInteractionEvent event, User userCaller, User opponent, String input) {
        this.event = event;
        this.user1 = userCaller;
        this.user2 = opponent;
        this.input = input;

        if (this.user2 == event.getJDA().getSelfUser()) {
            this.solo = true;
            start(input, options[cpuChoice.nextInt(options.length)]);
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
            start(this.input, this.input2);
        }
    }

    public void start(String input, String input2) {
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

        announceWinner(winner, input, input2);
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

    public void announceWinner(@NotNull User winner, String input, String input2) {
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
    }
}
