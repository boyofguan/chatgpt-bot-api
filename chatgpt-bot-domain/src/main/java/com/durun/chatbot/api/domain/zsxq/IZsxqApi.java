package com.durun.chatbot.api.domain.zsxq;

import com.durun.chatbot.api.domain.zsxq.model.aggregates.UnansweredQuestionsAggregates;

import java.io.IOException;

public interface IZsxqApi {

    UnansweredQuestionsAggregates queryUnansweredQuestionsTopicId(String groupId, String cookie) throws IOException;

    boolean answer(String groupId, String cookie, String topicId, String text) throws IOException;
}
