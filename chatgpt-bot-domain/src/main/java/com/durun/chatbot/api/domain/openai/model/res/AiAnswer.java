package com.durun.chatbot.api.domain.openai.model.res;

import com.durun.chatbot.api.domain.openai.model.vo.Choices;
import com.durun.chatbot.api.domain.openai.model.vo.Usage;

import java.util.List;

public class AiAnswer {
    private String id;

    private String object;

    private int created;

    private String model;

    private Usage usage;

    private List<Choices> choices;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setObject(String object){
        this.object = object;
    }
    public String getObject(){
        return this.object;
    }
    public void setCreated(int created){
        this.created = created;
    }
    public int getCreated(){
        return this.created;
    }
    public void setModel(String model){
        this.model = model;
    }
    public String getModel(){
        return this.model;
    }
    public void setUsage(Usage usage){
        this.usage = usage;
    }
    public Usage getUsage(){
        return this.usage;
    }
    public void setChoices(List<Choices> choices){
        this.choices = choices;
    }
    public List<Choices> getChoices(){
        return this.choices;
    }
}
