package com.example.pfairplayservice.cassandra;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CassandraConfig {

    public @Bean
    CqlSession session() {
        return CqlSession.builder().withKeyspace("pfairplayservice").build();
    }

}
