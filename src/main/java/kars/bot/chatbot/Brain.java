package kars.bot.chatbot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Random;

public class Brain {
    PromptType promptType;
    String prompt;
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
        if (prompt.contains("?")) {
            return PromptType.QUESTION;
        }
        else {
            return PromptType.UNKNOWN;
        }
    }

    private void respond() {
        switch(promptType) {
            case QUESTION -> {
                event.getChannel().sendMessage(Answers.questionAnswers[random.nextInt(7)]).queue();
            }
            case UNKNOWN -> {
                event.getChannel().sendMessage("I'm not sure what you meant").queue();
            }
        }
    }

    public void feedback(String prompt, String response, boolean feedback) {
        System.out.printf("%s was a response to %s and the positivity was %b", prompt, response, feedback);
    }



    public PromptType getPromptType() {
        return promptType;
    }
}
