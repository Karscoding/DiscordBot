package kars.bot.logging;
import net.dv8tion.jda.api.entities.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class LogScores {
    static JSONObject scores = new JSONObject();

    public static void saveScore(User user, int score) {
        String username = user.getGlobalName();
        long current = loadScore(username);
        scores.put(username, current++);
        try (FileWriter file = new FileWriter("scores.json")) {
            file.write(scores.toJSONString());
        }
        catch (IOException ie) {
            System.err.println("Error writing to file");
        }
    }

    public static long loadScore(String name) {
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader("scores.json")) {
            String content = reader.toString();
            if (content.isEmpty()) {
                return 0;
            }
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            if (jsonObject.get(name) != null) {
                return (Long) jsonObject.get(name);
            }
            else {
                return 0;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
