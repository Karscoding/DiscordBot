package kars.bot.games;

import kars.bot.DiscordBot;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Random;

public class Slots extends Game{
    double balance;
    double bet;
    double result;

    String message;

    SlashCommandInteractionEvent event;

    User player;

    Random random;

    public Slots(SlashCommandInteractionEvent event, User player, double balance, double bet) {
        super(event, player);
        setName("slots");

        this.balance = balance;
        this.bet = bet;

        random = new Random();
        start();
    }

    @Override
    public void start() {
        balance -= bet;
        result = bet * getMultiplier();
        announce();
    }

    public void announce() {
        DiscordBot.balanceLogger.saveValue(event.getUser(), result + balance);
        event.reply("You Won: " + result + " , " + message + "\nBalance: " + DiscordBot.balanceLogger.df.format(result + balance)).queue();
    }

    public double getMultiplier() {
        int seed = random.nextInt(1001);

        // 50%
        if (seed < 500) {
            message = "Too bad";
            return 0.15;
        }
        // 25%
        else if (seed < 750) {
            message = "Try again";
            return 0.25;
        }
        // 15%
        else if (seed < 900) {
            message = "Could be worse!";
            return 0.5;
        }
        // 5%
        else if (seed < 950) {
            message = "Broke even!";
            return 1;
        }
        // 2.5%
        else if (seed < 975) {
            message = "GREAT WIN";
            return 5;
        }
        // 1%
        else if (seed < 985) {
            message = "BIG WIN";
            return 25;
        }
        // 0.8%
        else if (seed < 993) {
            message = "HUGE WIN";
            return 75;
        }
        // 0.4%
        else if (seed < 997) {
            message = "MASSIVE WIN";
            return 100;
        }
        // 0.2%
        else if (seed < 999) {
            message = "INSANE WIN";
            return 200;
        }
        // 0.1%
        else if (seed == 1000) {
            message = "MAX WIN!";
            return 1500;
        }
        return 0.0;
    }
}
