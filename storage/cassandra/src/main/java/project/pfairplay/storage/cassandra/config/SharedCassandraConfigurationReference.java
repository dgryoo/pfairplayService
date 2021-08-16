package project.pfairplay.storage.cassandra.config;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@ComponentScan
@EnableCassandraRepositories("project.pfairplay.storage.cassandra.repository")
public class SharedCassandraConfigurationReference {

    public @Bean
    CqlSession session() {
        return CqlSession.builder().withKeyspace("pfairplayservice").build();
    }

}
