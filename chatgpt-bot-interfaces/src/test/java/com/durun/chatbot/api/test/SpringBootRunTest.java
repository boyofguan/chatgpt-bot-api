package com.durun.chatbot.api.test;

import com.alibaba.fastjson.JSON;
import com.durun.chatbot.api.domain.openai.IOpenAi;
import com.durun.chatbot.api.domain.zsxq.IZsxqApi;
import com.durun.chatbot.api.domain.zsxq.model.aggregates.UnansweredQuestionsAggregates;
import com.durun.chatbot.api.domain.zsxq.model.vo.Topics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRunTest {

    private Logger logger = LoggerFactory.getLogger(SpringBootRunTest.class);

    @Value("${chat-api.groupId}")
    private String groupId;
    @Value("${chat-api.cookie}")
    private String cookie;
    @Value("${open-ai.temperature}")
    private double temperature;
    @Value("${open-ai.model}")
    private String model;
    @Value("${open-ai.role}")
    private String role;
    @Value("${open-ai.api-key}")
    private String openai_key;
    @Resource
    private IZsxqApi zsxqApi;

    @Resource
    private IOpenAi openAi;

    @Test
    public void test_ZsxqApi() throws IOException {
        UnansweredQuestionsAggregates aggregates = zsxqApi.queryUnansweredQuestionsTopicId(groupId, cookie);
        logger.info("测试结果: {}", JSON.toJSONString(aggregates));
        if(!aggregates.isSucceeded()) return;
        List<Topics> topics = aggregates.getResp_data().getTopics();
        for(Topics topic : topics) {
            String topic_id = String.valueOf(topic.getTopic_id());
            String text = topic.getTalk().getText();
            if(topic.getShow_comments() != null) {
                logger.info("topic_id: {} text: {}", topic_id, text);
                zsxqApi.answer(groupId, cookie, topic_id, "不会");
            }
        }
    }
    @Test
    public void test_OpenAI() throws IOException {
        logger.info(openAi.doChatGPT("  女朋友冷暴力我怎么办", role, openai_key, model, temperature));
    }

}
