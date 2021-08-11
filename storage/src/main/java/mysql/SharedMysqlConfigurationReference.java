package mysql;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("mysql")
@EnableJpaRepositories("mysql.repository")
@EntityScan("mysql.model")
public class SharedMysqlConfigurationReference {

}
