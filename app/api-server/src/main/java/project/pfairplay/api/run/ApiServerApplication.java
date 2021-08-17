package project.pfairplay.api.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import project.pfairplay.storage.kafka.config.SharedKafkaProducerConfig;
import project.pfairplay.storage.kafka.config.SharedKafkaTopicConfig;
import project.pfairplay.storage.mysql.config.SharedMysqlConfigurationReference;
import project.pfairplay.storage.cassandra.config.SharedCassandraConfigurationReference;

@SpringBootApplication
@ComponentScan("project.pfairplay.api.controller")
@Import({SharedMysqlConfigurationReference.class, SharedCassandraConfigurationReference.class, SharedKafkaProducerConfig.class, SharedKafkaTopicConfig.class})
public class ApiServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiServerApplication.class, args);
    }

}
