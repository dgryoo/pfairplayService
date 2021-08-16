package project.pfairplay.storage.kafka.config;

import project.pfairplay.storage.kafka.model.TeamReviewThumbs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SharedKafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, TeamReviewThumbs> thumbsChangeConsumer() {

        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "thumbsGroup");

        return new DefaultKafkaConsumerFactory<>(
                configs,
                new StringDeserializer(),
                new JsonDeserializer<>(TeamReviewThumbs.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TeamReviewThumbs> thumbsChangeListener() {
        ConcurrentKafkaListenerContainerFactory<String, TeamReviewThumbs> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(thumbsChangeConsumer());
        return factory;
    }

}
