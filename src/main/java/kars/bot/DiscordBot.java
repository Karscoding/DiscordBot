package kars.bot;

import kars.bot.chatbot.Chat;
import kars.bot.events.MessageEventListener;
import kars.bot.events.MessageReactionAdd;
import kars.bot.events.ReadyEventListener;
import kars.bot.events.SlashCommand;
import kars.bot.games.RockPaperScissors;
import kars.bot.games.Slots;
import kars.bot.logging.Logger;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class DiscordBot {
    public static RockPaperScissors rps;
    public static Chat chat;
    public static Logger scoresLogger;
    public static Logger balanceLogger;
    public static Slots slots;
    public static boolean debug = true;

    public static void main(String[] args) {
        JDABuilder builder = JDABuilder.createDefault(Token.getToken());
        scoresLogger = new Logger("scores.json", "rps");
        balanceLogger = new Logger("balance.json", "slots");
        scoresLogger.initialize();
        balanceLogger.initialize();

        builder
                .addEventListeners(new ReadyEventListener(), new MessageEventListener(), new SlashCommand(), new MessageReactionAdd())
                .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES)
                .build();
    }
}
