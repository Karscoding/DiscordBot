package kars.bot.games;

import kars.bot.events.MessageHandler;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import java.util.Random;

public class RockPaperScissors {
    MessageReceivedEvent event;
    MessageHandler msg;
    User winner;

    public User user1;
    public User user2;

    String input;
    String input2;

    String[] options = {"Rock", "Paper", "Scissors"};
    Random cpuChoice = new Random();

    public boolean solo;

    public boolean awaitingInput;
    public boolean awaitingUser;

    public RockPaperScissors(MessageReceivedEvent event, MessageHandler msg, boolean solo, User userCaller) {
        this.msg = msg;
        this.event = event;
        this.user1 = userCaller;
        this.solo = solo;

        awaitingInput = true;
    }

    public void setOpponent(User user) {
        this.user2 = user;
    }

    public void giveInput(String input, User user) {
        if (user == this.user1 && this.input == null) {
            this.input = input;
            if (solo) {
                awaitingInput = false;
                start(input, options[cpuChoice.nextInt(options.length)]);
            }
        }

        else if (user == this.user2 && !solo) {
            this.input2 = input;
            if (this.input != null) {
                awaitingInput = false;
                start(this.input, this.input2);
            }
        }

        else if (this.input != null && this.input2 != null) {
            start(this.input, this.input2);
        }

        else if (user != this.user1 && user != this.user2){
            System.err.println("User was not part of game or gave wrong input");
        }
    }

    public void start(String input, String input2) {
        if (user2 == null) {
            user2 = event.getJDA().getSelfUser();
        }

        if (input.equalsIgnoreCase(input2)) {
            winner = null;
        }

        else if (input.equalsIgnoreCase("Rock")) {
            winner = input2.equalsIgnoreCase("Scissors") ? user1 : user2;
        }
        else if (input.equalsIgnoreCase("Scissors")) {
            winner = input2.equalsIgnoreCase("Paper") ? user1 : user2;
        }
        else if (input.equalsIgnoreCase("Paper")) {
            winner = input2.equalsIgnoreCase("Rock") ? user1 : user2;
        }

        if (winner == null) {
            msg.send("Tie Game!");
            return;
        }

        announceWinner(winner, input, input2);
    }

    public void announceWinner(@NotNull User winner, String input, String input2) {
        msg.send(user1.getEffectiveName() + " Picked : " + input + "\n");
        msg.send(user2.getEffectiveName() + " Picked : " + input2 + "\n");
        msg.send(winner.getEffectiveName() + " has won!");
    }
}
