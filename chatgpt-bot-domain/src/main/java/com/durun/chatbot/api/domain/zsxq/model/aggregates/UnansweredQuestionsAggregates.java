package com.durun.chatbot.api.domain.zsxq.model.aggregates;

import com.durun.chatbot.api.domain.zsxq.model.res.Resp_data;

/**
 * 未回答问题的聚合信息
 */
public class UnansweredQuestionsAggregates {
    private boolean succeeded;

    private Resp_data resp_data;

    public void setSucceeded(boolean succeeded){
        this.succeeded = succeeded;
    }
    public boolean isSucceeded(){
        return this.succeeded;
    }
    public void setResp_data(Resp_data resp_data){
        this.resp_data = resp_data;
    }
    public Resp_data getResp_data(){
        return this.resp_data;
    }
}
