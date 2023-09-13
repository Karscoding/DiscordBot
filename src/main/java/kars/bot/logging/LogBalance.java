package kars.bot.logging;

public class LogBalance implements Logging{
    public LogBalance() {
        Logging.initialize("balances.json");
    }
}
