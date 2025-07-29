package com.peaknote.demo;

import com.peaknote.demo.service.SubscriptionService;
import com.peaknote.demo.service.GraphService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SubscriptionTest {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private GraphService graphService;

    @Value("${notification-url}")
    private String webhookUrl;

    @Test
    void contextLoads() {
        // 测试 Spring 容器是否加载 SubscriptionService 和 GraphService
        assertNotNull(subscriptionService, "SubscriptionService 未注入");
        assertNotNull(graphService, "GraphService 未注入");
    }

    @Test
    void testWebhookUrlIsConfigured() {
        // 测试 webhookUrl 是否被正确注入
        assertNotNull(webhookUrl, "notification-url 配置未注入");
        assertFalse(webhookUrl.isBlank(), "notification-url 配置为空");
        System.out.println("Webhook URL = " + webhookUrl);
    }

    @Test
    void testCreateEventSubscriptionWithInvalidUser() {
        // 测试调用 createEventSubscription，传入假用户id，验证方法是否能正常运行（会捕获异常）
        try {
            subscriptionService.createEventSubscription("invalid-user-id-for-test");
            // 你可以根据业务改为断言日志输出或mock结果
        } catch (Exception e) {
            fail("createEventSubscription 方法抛出了异常: " + e.getMessage());
        }
    }
}
