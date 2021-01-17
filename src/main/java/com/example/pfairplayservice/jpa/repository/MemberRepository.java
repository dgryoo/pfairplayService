package com.example.pfairplayservice.jpa.repository;

import com.example.pfairplayservice.jpa.model.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, String> {

}
