package com.example.educationplatform.ai;

import jakarta.websocket.*;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.concurrent.CountDownLatch;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Spark4.0 Ultra AI 客户端（WebSocket版本，URL鉴权 + 流式输出）
 */
@Component
public class SparkAIClient {

    @Value("${iflytek.app-id}")
    private String appId;

    @Value("${iflytek.api-key}")
    private String apiKey;

    @Value("${iflytek.api-secret}")
    private String apiSecret;

    private static final String HOST = "spark-api.xf-yun.com";
    private static final String DOMAIN = "4.0Ultra";

    /**
     * 提问，返回AI的回答（流式输出）
     */
    public String ask(String question) {
        try {
            CountDownLatch latch = new CountDownLatch(1);
            StringBuilder finalAnswer = new StringBuilder();

            String wsUrl = buildWsUrl();

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            ClientEndpointConfig config = ClientEndpointConfig.Builder.create().build();

            container.connectToServer(new Endpoint() {
                @Override
                public void onOpen(Session session, EndpointConfig config) {
                    try {
                        session.addMessageHandler(String.class, message -> {
                            try {
                                JSONObject json = new JSONObject(message);
                                JSONObject payload = json.optJSONObject("payload");
                                if (payload != null) {
                                    JSONObject choices = payload.optJSONObject("choices");
                                    if (choices != null) {
                                        JSONArray textArray = choices.optJSONArray("text");
                                        if (textArray != null) {
                                            for (int i = 0; i < textArray.length(); i++) {
                                                String content = textArray.getJSONObject(i).getString("content");
                                                System.out.print(content); // 实时输出
                                                finalAnswer.append(content);
                                            }
                                        }
                                        int status = choices.optInt("status");
                                        if (status == 2) { // 完成标志
                                            try {
                                                session.close();
                                            } catch (Exception ignore) {}
                                            latch.countDown();
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                        // 发送提问请求
                        session.getAsyncRemote().sendText(buildRequest(question));
                    } catch (Exception e) {
                        e.printStackTrace();
                        latch.countDown();
                    }
                }

                @Override
                public void onClose(Session session, CloseReason closeReason) {
                    latch.countDown();
                }

                @Override
                public void onError(Session session, Throwable thr) {
                    thr.printStackTrace();
                    latch.countDown();
                }
            }, config, new URI(wsUrl));

            //使用 CountDownLatch.await() 方法等待响应完成，等待AI回答结束
            latch.await();

            System.out.println("\n✅ 完整答案: " + finalAnswer);

            //finalAnswer是StringBuilder类型，调用toString()方法转换为String类型返回
            return finalAnswer.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "调用 SparkAI 出错：" + e.getMessage();
        }
    }

    /**
     * 构建带鉴权的 WebSocket URL
     */
    private String buildWsUrl() throws Exception {
        // GMT 时间
        String date = DateTimeFormatter.RFC_1123_DATE_TIME
                .withZone(ZoneId.of("GMT"))
                .format(Instant.now());

        // 拼接签名原文
        String signatureOrigin = "host: " + HOST + "\n" +
                "date: " + date + "\n" +
                "GET /v4.0/chat HTTP/1.1";

        // HMAC-SHA256 签名
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] signBytes = mac.doFinal(signatureOrigin.getBytes(StandardCharsets.UTF_8));
        String signature = Base64.getEncoder().encodeToString(signBytes);

        // 构建 authorization 并 Base64
        String authorization = String.format(
                "api_key=\"%s\", algorithm=\"hmac-sha256\", headers=\"host date request-line\", signature=\"%s\"",
                apiKey, signature
        );
        String authorizationBase64 = Base64.getEncoder().encodeToString(authorization.getBytes(StandardCharsets.UTF_8));

        // 最终 WebSocket URL
        return String.format("wss://%s/v4.0/chat?authorization=%s&date=%s&host=%s",
                HOST,
                authorizationBase64,
                java.net.URLEncoder.encode(date, "UTF-8"),
                HOST);
    }

    /**
     * 构造提问请求 JSON
     */
    private String buildRequest(String question) {
        return "{\n" +
                "  \"header\": {\"app_id\": \"" + appId + "\", \"uid\": \"1234\"},\n" +
                "  \"parameter\": {\"chat\": {\"domain\": \"" + DOMAIN + "\", \"temperature\": 0.5}},\n" +
                "  \"payload\": {\"message\": {\"text\": [{\"role\": \"user\", \"content\": \"" + question + "\"}]}}\n" +
                "}";
    }
}
