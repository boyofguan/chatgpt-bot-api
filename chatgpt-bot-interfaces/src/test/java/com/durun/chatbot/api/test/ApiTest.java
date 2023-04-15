package com.durun.chatbot.api.test;


import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

public class ApiTest {
    @Test
    public void query_unanswered_questions() throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/15552518841512/topics?scope=all&count=20");

        get.addHeader("cookie", "zsxq_access_token=7AADDF18-7D65-CFA3-0526-8C57ABCAF7E4_68FFCC7EEC3E7017; sensorsdata2015jssdkcross={\"distinct_id\":\"186fe00e4dce2-09eaa62bf74a848-26021e51-1638720-186fe00e4dd9f0\",\"first_id\":\"\",\"props\":{\"$latest_traffic_source_type\":\"直接流量\",\"$latest_search_keyword\":\"未取到值_直接打开\",\"$latest_referrer\":\"\"},\"identities\":\"eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTg2ZmUwMGU0ZGNlMi0wOWVhYTYyYmY3NGE4NDgtMjYwMjFlNTEtMTYzODcyMC0xODZmZTAwZTRkZDlmMCJ9\",\"history_login_id\":{\"name\":\"\",\"value\":\"\"},\"$device_id\":\"186fe00e4dce2-09eaa62bf74a848-26021e51-1638720-186fe00e4dd9f0\"}; zsxqsessionid=475f20f07a27f04cdd12bb901b76ab0d; abtest_env=product");
        get.addHeader("Content-Type", "application/json; charset=UTF-8");

        CloseableHttpResponse response = httpClient.execute(get);

        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void answer() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/584554852121854/comments");

        post.addHeader("cookie", "zsxq_access_token=7AADDF18-7D65-CFA3-0526-8C57ABCAF7E4_68FFCC7EEC3E7017; sensorsdata2015jssdkcross={\"distinct_id\":\"186fe00e4dce2-09eaa62bf74a848-26021e51-1638720-186fe00e4dd9f0\",\"first_id\":\"\",\"props\":{\"$latest_traffic_source_type\":\"直接流量\",\"$latest_search_keyword\":\"未取到值_直接打开\",\"$latest_referrer\":\"\"},\"identities\":\"eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTg2ZmUwMGU0ZGNlMi0wOWVhYTYyYmY3NGE4NDgtMjYwMjFlNTEtMTYzODcyMC0xODZmZTAwZTRkZDlmMCJ9\",\"history_login_id\":{\"name\":\"\",\"value\":\"\"},\"$device_id\":\"186fe00e4dce2-09eaa62bf74a848-26021e51-1638720-186fe00e4dd9f0\"}; zsxqsessionid=475f20f07a27f04cdd12bb901b76ab0d; abtest_env=product");
        post.addHeader("Content-Type", "application/json; charset=UTF-8");

        String paramJson = "{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"放屁\\n\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"mentioned_user_ids\": []\n" +
                "  }\n" +
                "}";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);
        CloseableHttpResponse response = httpClient.execute(post);

        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void test_ChatGPT() throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create().setProxy(new HttpHost("127.0.0.1", 7890)).build();
            HttpPost post = new HttpPost("https://api.openai.com/v1/chat/completions");
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Bearer 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");

        String paramJson = "{\n" +
                "     \"model\": \"gpt-3.5-turbo\",\n" +
                "     \"messages\": [{\"role\": \"user\", \"content\": \"帮我写一个冒泡排序\"}],\n" +
                "     \"temperature\": 0.7\n" +
                "   }";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);
        CloseableHttpResponse response = httpClient.execute(post);

        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }
}
