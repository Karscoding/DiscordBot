package kars.bot.games;

import kars.bot.DiscordBot;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.Random;

public class Slots extends Game{
    double balance;
    double bet;
    double result;

    SlashCommandInteractionEvent event;

    User player;

    Random random;

    public Slots(SlashCommandInteractionEvent event, User player, double balance, double bet) {
        setName("slots");

        this.event = event;
        this.player = player;
        this.balance = balance;
        this.bet = bet;

        random = new Random();
        start();
    }

    @Override
    public void start() {
        balance -= bet;
        int seed = random.nextInt(15);
        result = (bet * (double) seed) / 10;
        announce();
    }

    public void announce() {
        DiscordBot.balanceLogger.saveValue(event.getUser(), result + balance, "slots");
        event.reply(player.getEffectiveName() + " Gambled " + bet + " \nResult: " + result + "\nCurrent Balance: " + (balance + result)).queue();
    }
}
