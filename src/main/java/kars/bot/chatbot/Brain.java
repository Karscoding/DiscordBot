package kars.bot.chatbot;

import kars.bot.DiscordBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Brain {
    PromptType promptType;
    String prompt;
    String response;
    MessageReceivedEvent event;
    Random random;

    public Brain(MessageReceivedEvent event, String prompt) {
        this.event = event;
        this.prompt = prompt;
        promptType = findType();

        random = new Random();

        respond();
    }

    public String getPrompt() {
        return prompt;
    }

    private PromptType findType() {
        if (prompt.contains("?") || prompt.contains("do you") || prompt.contains("Do you")) {
            return PromptType.QUESTION;
        }
        else {
            return PromptType.UNKNOWN;
        }
    }

    private void respond() {
        response = findBestResponse();
        event.getChannel().sendMessage(response).queue();
    }

    public String findBestResponse() {
        switch(promptType) {
            case QUESTION -> {
                HashMap<String, Long> scores = DiscordBot.feedbackLogger.loadValues(prompt);
                if (Collections.max(scores.entrySet(), Map.Entry.comparingByValue()).getValue() <= 0) {
                    return Answers.questionAnswers[random.nextInt(7)];
                }
                else {
                    return Collections.max(scores.entrySet(), Map.Entry.comparingByValue()).getKey();
                }
            }
            case UNKNOWN -> {
                return "I'm not sure what you meant";
            }
        }
        return "I'm not sure what you meant";
    }

    public void feedback(boolean feedback) {
        System.out.printf("%s was a response to %s and the positivity was %b", response, prompt, feedback);

        DiscordBot.feedbackLogger.saveValue(prompt, response, feedback);
    }

    public PromptType getPromptType() {
        return promptType;
    }
}
