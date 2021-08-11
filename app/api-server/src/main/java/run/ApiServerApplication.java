package run;

import cassandra.SharedCassandraConfigurationReference;
import mysql.SharedMysqlConfigurationReference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan("controller")
@Import({SharedMysqlConfigurationReference.class, SharedCassandraConfigurationReference.class})
public class ApiServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiServerApplication.class, args);
    }

}
