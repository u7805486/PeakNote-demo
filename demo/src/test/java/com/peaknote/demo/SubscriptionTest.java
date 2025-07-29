package com.peaknote.demo;

import com.microsoft.graph.models.Subscription;

import com.peaknote.demo.service.SubscriptionService;
import com.peaknote.demo.service.GraphService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@SpringBootTest(properties = {"notification-url=http://localhost:8080/"})
public class SubscriptionTest {

    @Autowired
    private SubscriptionService subscriptionService;

    @MockBean
    private GraphService graphService; // Mock GraphService

    @Value("${notification-url}")
    private String webhookUrl;

    @Test
    void contextLoads() {
        assertNotNull(subscriptionService, "SubscriptionService 未注入");
        assertNotNull(graphService, "GraphService 未注入（Mock）");
    }

    @Test
    void testWebhookUrlIsConfigured() {
        assertNotNull(webhookUrl, "notification-url 配置未注入");
        assertFalse(webhookUrl.isBlank(), "notification-url 配置为空");
        System.out.println("Webhook URL = " + webhookUrl);
    }

    @Test
    void testCreateEventSubscriptionWithInvalidUser() {
        // Mock graphService.createEventSubscription 返回一个假的 Subscription 对象
        Subscription fakeSub = new Subscription();
        fakeSub.id = "fake-subscription-id";
        Mockito.when(graphService.createEventSubscription(
                anyString(),
                anyString(),
                anyString(),
                any(OffsetDateTime.class)
        )).thenReturn(fakeSub);

        // 调用方法不应该抛异常
        assertDoesNotThrow(() -> subscriptionService.createEventSubscription("invalid-user-id-for-test"));
    }
}
