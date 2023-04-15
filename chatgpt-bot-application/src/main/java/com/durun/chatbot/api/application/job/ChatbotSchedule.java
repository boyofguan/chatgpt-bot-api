package com.durun.chatbot.api.application.job;

import com.alibaba.fastjson.JSON;
import com.durun.chatbot.api.domain.openai.IOpenAi;
import com.durun.chatbot.api.domain.zsxq.IZsxqApi;
import com.durun.chatbot.api.domain.zsxq.model.aggregates.UnansweredQuestionsAggregates;
import com.durun.chatbot.api.domain.zsxq.model.vo.Topics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

@EnableScheduling
@Configuration
public class ChatbotSchedule {

    private Logger logger = LoggerFactory.getLogger(ChatbotSchedule.class);
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


    //设置轮询，每多久跑一次，可以用在线cron生成器来生成该表达式
    @Scheduled(cron = "0/5 * * * * ? ")
    public void run() {
        try {
            if(new Random().nextBoolean()) {
                logger.info("随机打烊中...");
                return;
            }

            //设置夜间22点-次日7点不工作
            GregorianCalendar calendar = new GregorianCalendar();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            if(hour > 22 || hour < 7) {
                logger.info("打烊时间不工作，下班了！");
                return;
            }


            UnansweredQuestionsAggregates aggregates = zsxqApi.queryUnansweredQuestionsTopicId(groupId, cookie);
            logger.info("测试结果: {}", JSON.toJSONString(aggregates));

            List<Topics> topicsList = aggregates.getResp_data().getTopics();
            if(topicsList == null || topicsList.isEmpty()) {
                logger.info("本次检索没有查询到问题");
                return;
            }

            for (Topics topics : topicsList) {
                if(topics.getShow_comments() == null) {
                    String answer = openAi.doChatGPT(topics.getTalk().getText().trim(), role, openai_key, model, temperature);
                    boolean status = zsxqApi.answer(groupId, cookie, String.valueOf(topics.getTopic_id()), answer);
                    logger.info("编号: {} 问题: {} 回答: {} 状态: {}", topics.getTopic_id(), topics.getTalk().getText(), answer, status);
                    break;
                }

            }


        } catch (Exception e) {
            logger.error("自动回答问题异常: ", e);
        }
    }
}
