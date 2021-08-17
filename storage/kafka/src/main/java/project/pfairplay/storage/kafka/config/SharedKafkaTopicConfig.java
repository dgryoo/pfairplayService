package project.pfairplay.storage.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class SharedKafkaTopicConfig {

    @Bean
    public NewTopic createThumbsTopic() {
        return TopicBuilder.name("thumbs")
                .partitions(2)
                .replicas(1)
                .config(TopicConfig.COMPRESSION_TYPE_CONFIG, "gzip")
                .build();
    }

}
