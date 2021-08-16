package project.pfairplay.storage.mysql.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan
@EnableJpaRepositories("project.pfairplay.storage.mysql.repository")
@EntityScan("project.pfairplay.storage.mysql.model")
public class SharedMysqlConfigurationReference {

}
