package com.durun.chatbot.api.domain.openai.model.res;

public class Message
{
    private String role;

    private String content;

    public void setRole(String role){
        this.role = role;
    }
    public String getRole(){
        return this.role;
    }
    public void setContent(String content){
        this.content = content;
    }
    public String getContent(){
        return this.content;
    }
}