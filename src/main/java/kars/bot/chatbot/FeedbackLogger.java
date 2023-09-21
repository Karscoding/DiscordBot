package kars.bot.chatbot;

import kars.bot.Console;
import net.dv8tion.jda.api.entities.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Set;

public class FeedbackLogger {
    JSONObject promptObject;
    JSONObject scoreObject;
    static JSONParser jsonParser;
    String filename;

    public FeedbackLogger(String filename, JSONObject current) {
        this.filename = filename;
        this.promptObject = current;

        scoreObject = new JSONObject();
    }

    public static FeedbackLogger initialize(String filename) {
        JSONObject current = new JSONObject();
        jsonParser = new JSONParser();
        try (Reader reader = new FileReader(filename)) {
            current = (JSONObject) jsonParser.parse(reader);

            Set<String> keyset = current.keySet();
            for (String key : keyset) {
                Object value = current.get(key);
                current.put(key, value);
            }
        }
        catch (IOException | ParseException e) {
            System.err.println(e);
        }

        return new FeedbackLogger(filename, current);
    }

    public void saveValue(String prompt, String answer, boolean positivity) {
        long currentScore;
        HashMap<String, Long> currents = loadValues(prompt);

        if (currents.get(answer) != null) {
            currentScore = currents.get(answer);
        }
        else {
            currentScore = 0;
        }

        currentScore = positivity ? currentScore + 1 : currentScore - 1;

        scoreObject.put(answer, currentScore);
        promptObject.put(prompt,scoreObject);


        try (FileWriter file = new FileWriter(filename)) {
            file.write(promptObject.toJSONString());
        }

        catch (IOException ie) {
            System.err.println("Error writing to file");
        }
    }

    public HashMap<String, Long> loadValues(String prompt) {
        HashMap<String, Long> scores = new HashMap<>();

        try (Reader reader = new FileReader(filename)) {
            String content = reader.toString();

            JSONObject prompts = (JSONObject) jsonParser.parse(reader);
            if (!prompts.containsKey(prompt)) {
                scores.put("", 0L);
                return scores;
            }
            JSONObject answers = (JSONObject) prompts.get(prompt);

            Set<String> keyset = answers.keySet();
            for (String key : keyset) {
                long value = (long) answers.get(key);
                scores.put(key, value);
            }
        }

        catch (NoSuchElementException | IOException | ParseException e) {
            System.err.println(e);
        }

        return scores;
    }
}
