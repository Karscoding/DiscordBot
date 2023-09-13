package kars.bot.logging;

import net.dv8tion.jda.api.entities.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Set;

public interface Logging {
    JSONObject jsonObject = new JSONObject();
    JSONParser jsonParser = new JSONParser();

    static void initialize(String filename) {
        try (Reader reader = new FileReader(filename)) {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            Set<String> keyset = jsonObject.keySet();
            for (String key : keyset) {
                Object value = jsonObject.get(key);
                jsonObject.put(key, value);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

//    static void save(User user, int score) {
//        String username = user.getGlobalName();
//        if (username == null) {
//            username = "Karscoding";
//        }
//        System.out.println(username);
//        long current = loadScore(username);
//        current++;
//        scores.put(username, current);
//        try (FileWriter file = new FileWriter("scores.json")) {
//            file.write(scores.toJSONString());
//        }
//        catch (IOException ie) {
//            System.err.println("Error writing to file");
//        }
//    }
}
