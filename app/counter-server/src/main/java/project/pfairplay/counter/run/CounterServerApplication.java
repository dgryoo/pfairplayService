package project.pfairplay.counter.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import project.pfairplay.storage.cassandra.config.SharedCassandraConfigurationReference;
import project.pfairplay.storage.kafka.config.SharedKafkaConsumerConfig;
import project.pfairplay.storage.mysql.config.SharedMysqlConfigurationReference;

@SpringBootApplication
@ComponentScan("project.pfairplay.counter.controller")
@Import({SharedMysqlConfigurationReference.class, SharedCassandraConfigurationReference.class, SharedKafkaConsumerConfig.class})
public class CounterServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CounterServerApplication.class, args);
    }

}
