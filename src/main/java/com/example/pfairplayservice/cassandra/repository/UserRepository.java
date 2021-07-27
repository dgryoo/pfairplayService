package com.example.pfairplayservice.cassandra.repository;

import com.example.pfairplayservice.cassandra.model.User;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface UserRepository extends CassandraRepository<User, Integer> {
}
