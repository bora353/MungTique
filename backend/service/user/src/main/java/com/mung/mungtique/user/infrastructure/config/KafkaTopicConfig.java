package com.mung.mungtique.user.infrastructure.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
@RequiredArgsConstructor
public class KafkaTopicConfig {

    private final KafkaAdmin kafkaAdmin;

    private final static String TOPIC_USER = "user";
    private final static String TOPIC_CARE = "care";

    private NewTopic userTopic() {
        return TopicBuilder.name(TOPIC_USER)
                .partitions(1)
                .build();
    }

    private NewTopic careTopic() {
        return TopicBuilder.name(TOPIC_CARE)
                .partitions(1)
                .build();
    }

    @PostConstruct
    public void init() {
        kafkaAdmin.createOrModifyTopics(userTopic());
        kafkaAdmin.createOrModifyTopics(careTopic());
    }
}
