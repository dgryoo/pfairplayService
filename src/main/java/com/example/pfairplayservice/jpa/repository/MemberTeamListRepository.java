package com.example.pfairplayservice.jpa.repository;

import com.example.pfairplayservice.jpa.model.MemberTeamListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberTeamListRepository extends JpaRepository<MemberTeamListEntity, String> {

    //TODO create method findMemberByTid, findTeamByUid

}
