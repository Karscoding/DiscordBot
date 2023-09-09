package kars.bot;

import kars.bot.events.MessageEventListener;
import kars.bot.events.ReadyEventListener;
import kars.bot.games.RockPaperScissors;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class DiscordBot {
    public static RockPaperScissors rps;
    public static void main(String[] args) {
        JDABuilder builder = JDABuilder.createDefault(Token.getToken());

        builder
                .addEventListeners(new ReadyEventListener(), new MessageEventListener())
                .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES)
                .build();
    }
}
