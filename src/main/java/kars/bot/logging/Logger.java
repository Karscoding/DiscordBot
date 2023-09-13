package kars.bot.logging;

import kars.bot.Console;
import kars.bot.DiscordBot;
import kars.bot.games.Game;
import kars.bot.games.Slots;
import net.dv8tion.jda.api.entities.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

@SuppressWarnings("ALL")
public class Logger {
    JSONObject jsonObject = new JSONObject();
    JSONParser jsonParser = new JSONParser();
    String filename;

    public Logger(String filename) {
        this.filename = filename;
    }

    public void initialize() {
        try (Reader reader = new FileReader(filename)) {
            jsonObject = (JSONObject) jsonParser.parse(reader);

            Set<String> keyset = jsonObject.keySet();
            for (String key : keyset) {
                Object value = jsonObject.get(key);
                jsonObject.put(key, value);
            }
        }
        catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveValue(User user, double value, String game) {
        String username = user.getGlobalName();
        if (username == null) {
            return;
        }
        Console.debug(username);

        double current = loadValue(username);
        if (game.equals("slots")) {
            current = value;
        }
        else {
            current += value;
        }
        jsonObject.put(username, current);

        try (FileWriter file = new FileWriter(filename)) {
            file.write(jsonObject.toJSONString());
        }
        catch (IOException ie) {
            System.err.println("Error writing to file");
        }
    }

    public double loadValue(String name) {
        try (Reader reader = new FileReader(filename)) {
            String content = reader.toString();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            if (jsonObject.get(name) != null) {
                return (Double) jsonObject.get(name);
            }
            else {
                Console.debug("name was null");
                return 0;
            }
        }
        catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public String readAll() {
        StringBuilder sb = new StringBuilder();

        try (Reader reader = new FileReader(filename)) {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            Set<String> keyset = jsonObject.keySet();
            HashMap<String, Double> scores = new HashMap<>();

            for (String key : keyset) {
                Object value = jsonObject.get(key);
                scores.put(key, (Double) value);
            }

            scores = sortAll(scores);

            for (Map.Entry<String, Double> entry : scores.entrySet()) {
                sb.append(entry.getKey()).append(" : ").append(entry.getValue()).append("\n");
            }
        }
        catch (IOException ie) {
            return "";
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return sb.toString();
    }

    public HashMap<String, Double> sortAll(HashMap<String, Double> scores) {
        LinkedHashMap<String, Double> result = new LinkedHashMap<>();
        ArrayList<Double> list = new ArrayList<>();

        for (Map.Entry<String, Double> entry : scores.entrySet()) {
            list.add(entry.getValue());
        }

        Collections.sort(list, Collections.reverseOrder());

        for (double num : list) {
            for (Map.Entry<String, Double> entry : scores.entrySet()) {
                if (entry.getValue().equals(num)) {
                    result.put(entry.getKey(), num);
                }
            }
        }

        return result;
    }
}
