package com.mung.mungtique.mungshop.infrastructure.config;

import com.mung.mungtique.mungshop.adaptor.in.message.dto.MungShopConfirmMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@Slf4j
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    public <T> ConsumerFactory<String, T> consumerFactory(Class<T> type) {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        config.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, KafkaProperties.IsolationLevel.READ_COMMITTED.toString().toLowerCase());

        JsonDeserializer<T> jsonDeserializer = new JsonDeserializer<>(type, false);
        jsonDeserializer.addTrustedPackages("*");
        jsonDeserializer.setRemoveTypeHeaders(false);
        jsonDeserializer.setUseTypeHeaders(true);

        ErrorHandlingDeserializer<String> keyDeserializer = new ErrorHandlingDeserializer<>(new StringDeserializer());
        ErrorHandlingDeserializer<T> valueDeserializer = new ErrorHandlingDeserializer<>(jsonDeserializer);

        return new DefaultKafkaConsumerFactory<>(config, keyDeserializer, valueDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MungShopConfirmMessage> mungShopKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MungShopConfirmMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(MungShopConfirmMessage.class));
        factory.setCommonErrorHandler(new DefaultErrorHandler((record, ex) -> {
            log.error("Kafka Consumer Error: {} \nRecord: {}", ex.getMessage(), record, ex);
        }, new FixedBackOff(1000, 3)));
        factory.setConcurrency(1);
        factory.setAutoStartup(true);

        return factory;
    }
}
