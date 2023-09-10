package kars.bot;

public class Console {
    public static void debug(String message) {
        if (DiscordBot.debug) {
            System.out.println(message);
        }
    }
}
