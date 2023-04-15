package com.durun.chatbot.api.domain.openai.service;

import com.alibaba.fastjson.JSON;
import com.durun.chatbot.api.domain.openai.IOpenAi;
import com.durun.chatbot.api.domain.openai.model.req.AiQuestion;
import com.durun.chatbot.api.domain.openai.model.req.Messages;
import com.durun.chatbot.api.domain.openai.model.res.AiAnswer;
import com.durun.chatbot.api.domain.zsxq.model.res.AnswerRes;
import net.sf.json.JSONObject;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public class OpenAi implements IOpenAi {

    private Logger logger = LoggerFactory.getLogger(OpenAi.class);
    @Override
    public String doChatGPT(String question, String role, String openai_key, String model, double temperature) throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create().setProxy(new HttpHost("127.0.0.1", 7890)).build();
        HttpPost post = new HttpPost("https://api.openai.com/v1/chat/completions");
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Bearer " + openai_key);



        Messages messages = new Messages(role, question);
        List<Messages> messagesList = new ArrayList<>();
        messagesList.add(messages);
        AiQuestion aiQuestion = new AiQuestion(model, messagesList, temperature);
        String paramJson = JSONObject.fromObject(aiQuestion).toString();

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);
        CloseableHttpResponse response = httpClient.execute(post);

        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String jsonStr = EntityUtils.toString(response.getEntity());
            AiAnswer aiAnswer = JSON.parseObject(jsonStr, AiAnswer.class);
            logger.info("返回成功！");
            return aiAnswer.getChoices().get(0).getMessage().getContent();
        } else {
            logger.info(String.valueOf(response.getStatusLine().getStatusCode()));
            return null;
        }

    }
}
