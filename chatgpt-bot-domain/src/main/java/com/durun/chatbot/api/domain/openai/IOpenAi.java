package com.durun.chatbot.api.domain.openai;

import java.io.IOException;

public interface IOpenAi {

    String doChatGPT(String question, String role, String openai_key, String model, double temperature) throws IOException;
}
