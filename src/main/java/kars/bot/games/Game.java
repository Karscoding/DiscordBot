package kars.bot.games;

public abstract class Game {
    private static String name;

    public String getName() {
        return name;
    }
    public void setName(String name) { Game.name = name; }

    public abstract void start();
}
