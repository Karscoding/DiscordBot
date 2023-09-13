package kars.bot.logging;
import net.dv8tion.jda.api.entities.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Set;

public class LogScores implements Logging{
    static JSONObject scores = new JSONObject();
    static JSONParser parser = new JSONParser();

    @SuppressWarnings("unchecked")
    public static void initialize() {
        try (Reader reader = new FileReader("scores.json")) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            Set<String> keyset = jsonObject.keySet();
            for (String key : keyset) {
                Object value = jsonObject.get(key);
                scores.put(key, value);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static void saveValue(User user, int score) {
        String username = user.getGlobalName();
        if (username == null) {
            username = "Karscoding";
        }
        System.out.println(username);
        long current = loadValue(username);
        current++;
        scores.put(username, current);
        try (FileWriter file = new FileWriter("scores.json")) {
            file.write(scores.toJSONString());
        }
        catch (IOException ie) {
            System.err.println("Error writing to file");
        }
    }

    public static long loadValue(String name) {
        try (Reader reader = new FileReader("scores.json")) {
            String content = reader.toString();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            if (jsonObject.get(name) != null) {
                return (Long) jsonObject.get(name);
            }
            else {
                System.out.println("name was null");
                return 0;
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readAll() {
        StringBuilder sb = new StringBuilder();
        try (Reader reader = new FileReader("scores.json")) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            Set<String> keyset = jsonObject.keySet();
            for (String key : keyset) {
                Object value = jsonObject.get(key);
                sb.append(key).append(" ").append(value).append("\n");
            }
        }
        catch (IOException ie) {
            return "";
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return sb.toString();
    }
}
