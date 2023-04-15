package com.durun.chatbot.api.domain.openai.model.req;

import org.springframework.stereotype.Component;

import java.util.List;
public class AiQuestion
{
    private String model;

    private List<Messages> messages;

    private double temperature;

    public AiQuestion(String model, List<Messages> messages, double temperature) {
        this.model = model;
        this.messages = messages;
        this.temperature = temperature;
    }

    public void setModel(String model){
        this.model = model;
    }
    public String getModel(){
        return this.model;
    }
    public void setMessages(List<Messages> messages){
        this.messages = messages;
    }
    public List<Messages> getMessages(){
        return this.messages;
    }
    public void setTemperature(double temperature){
        this.temperature = temperature;
    }
    public double getTemperature(){
        return this.temperature;
    }
}