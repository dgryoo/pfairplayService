package com.example.pfairplayservice.cassandra.repository;

import com.example.pfairplayservice.cassandra.model.CTeamEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface CTeamRepository extends CassandraRepository<CTeamEntity, String> {


}
