package kars.bot;

import kars.bot.events.MessageEventListener;
import kars.bot.events.ReadyEventListener;
import kars.bot.events.SlashCommand;
import kars.bot.games.RockPaperScissors;
import kars.bot.games.Slots;
import kars.bot.logging.LogBalance;
import kars.bot.logging.LogScores;
import kars.bot.logging.Logging;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class DiscordBot {
    public static RockPaperScissors rps;
    public static Slots slots;
    public static boolean debug = true;

    public static void main(String[] args) {
        JDABuilder builder = JDABuilder.createDefault(Token.getToken());
        LogScores.initialize();
        Logging.initialize("balance.json");

        builder
                .addEventListeners(new ReadyEventListener(), new MessageEventListener(), new SlashCommand())
                .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES)
                .build();
    }
}
