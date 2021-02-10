package com.example.pfairplayservice.jpa.repository;


import com.example.pfairplayservice.jpa.id.MemberTeamId;
import com.example.pfairplayservice.jpa.model.MemberEntity;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import com.example.pfairplayservice.jpa.model.MemberTeamEntity;
import com.example.pfairplayservice.jpa.model.TeamEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class MemberRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MemberTeamRepository memberTeamRepository;

    @Test
    public void whenFindById_thenReturnMemberEntity() {
        MemberEntity memberEntity = MemberEntity
                .builder()
                .id("ryoo")
                .password("testpw")
                .address("testad")
                .birthday(new Date())
                .joinDate(new Date())
                .level(1)
                .preferPosition(1)
                .phoneNumber("010")
                .recentLoginDate(new Date())
                .phoneNumberDisclosureOption(1)
                .name("ryoo2")
                .build();

        entityManager.persist(memberEntity);
        entityManager.flush();

        Optional<MemberEntity> found = memberRepository.findById(memberEntity.getUid());

        assertThat(found.get().getUid(), is(equalTo(memberEntity.getUid())));
    }

    @Test
    public void whenFindByMemberTeamTid_thenReturnMemberEntity() {
        MemberEntity memberEntity = MemberEntity
                .builder()
                .id("ryoo")
                .password("testpw")
                .address("testad")
                .birthday(new Date())
                .joinDate(new Date())
                .level(1)
                .preferPosition(1)
                .phoneNumber("010")
                .recentLoginDate(new Date())
                .phoneNumberDisclosureOption(1)
                .name("ryoo2")
                .build();

        TeamEntity teamEntity = TeamEntity
                .builder()
                .teamName("teamnametest")
                .teamLeadMember(memberEntity)
                .activityAreaAddress("seoul")
                .registrationDate(new Date())
                .foundDate(new Date())
                .build();



        memberRepository.save(memberEntity);
        teamRepository.save(teamEntity);

        Optional<TeamEntity> foundTeamEntity = teamRepository.findById(teamEntity.getTid());

        MemberTeamId memberTeamId = MemberTeamId
                .builder()
                .uid(memberEntity.getUid())
                .tid(teamEntity.getTid())
                .build();

        MemberTeamEntity memberTeamEntity = MemberTeamEntity
                .builder()
                .memberTeamId(memberTeamId)
                .build();

        memberTeamRepository.save(memberTeamEntity);

        Optional<MemberTeamEntity> foundMemberTeamEntity = memberTeamRepository.findById(memberTeamId);

        MemberEntity foundMemberEntity = memberRepository.findByMemberTeamIdTid(teamEntity.getTid())
                .stream()
                .iterator()
                .next();

        assertThat(foundMemberEntity.getUid(), is(equalTo(memberEntity.getUid())));
    }

    @Test
    public void whenFindByMemberTeamTid_thenReturnMemberEntityList() {
        MemberEntity memberEntity0 = MemberEntity
                .builder()
                .id("ryoo0")
                .password("testpw0")
                .address("testad0")
                .birthday(new Date())
                .joinDate(new Date())
                .level(1)
                .preferPosition(1)
                .phoneNumber("010")
                .recentLoginDate(new Date())
                .phoneNumberDisclosureOption(1)
                .name("ryoo0")
                .build();

        MemberEntity memberEntity1 = MemberEntity
                .builder()
                .id("ryoo1")
                .password("testpw1")
                .address("testad1")
                .birthday(new Date())
                .joinDate(new Date())
                .level(1)
                .preferPosition(1)
                .phoneNumber("010")
                .recentLoginDate(new Date())
                .phoneNumberDisclosureOption(1)
                .name("ryoo1")
                .build();

        MemberEntity memberEntity2 = MemberEntity
                .builder()
                .id("ryoo2")
                .password("testpw2")
                .address("testad2")
                .birthday(new Date())
                .joinDate(new Date())
                .level(1)
                .preferPosition(1)
                .phoneNumber("010")
                .recentLoginDate(new Date())
                .phoneNumberDisclosureOption(1)
                .name("ryoo2")
                .build();

        TeamEntity teamEntity = TeamEntity
                .builder()
                .teamName("teamnametest")
                .teamLeadMember(memberEntity0)
                .activityAreaAddress("seoul")
                .registrationDate(new Date())
                .foundDate(new Date())
                .build();



        memberRepository.save(memberEntity0);
        memberRepository.save(memberEntity1);
        memberRepository.save(memberEntity2);
        teamRepository.save(teamEntity);

        Optional<TeamEntity> foundTeamEntity = teamRepository.findById(teamEntity.getTid());

        MemberTeamId memberTeamId0 = MemberTeamId
                .builder()
                .uid(memberEntity0.getUid())
                .tid(teamEntity.getTid())
                .build();

        MemberTeamId memberTeamId1 = MemberTeamId
                .builder()
                .uid(memberEntity0.getUid())
                .tid(teamEntity.getTid())
                .build();

        MemberTeamId memberTeamId2 = MemberTeamId
                .builder()
                .uid(memberEntity0.getUid())
                .tid(teamEntity.getTid())
                .build();

        MemberTeamEntity memberTeamEntity0 = MemberTeamEntity
                .builder()
                .memberTeamId(memberTeamId0)
                .build();

        MemberTeamEntity memberTeamEntity1 = MemberTeamEntity
                .builder()
                .memberTeamId(memberTeamId0)
                .build();

        MemberTeamEntity memberTeamEntity2 = MemberTeamEntity
                .builder()
                .memberTeamId(memberTeamId0)
                .build();

        memberTeamRepository.save(memberTeamEntity0);
        memberTeamRepository.save(memberTeamEntity1);
        memberTeamRepository.save(memberTeamEntity2);

        List<MemberEntity> found = memberRepository.findByMemberTeamIdTid(teamEntity.getTid());

        List<MemberEntity> saveMemberEntityList = new ArrayList<>();

        saveMemberEntityList.add(memberEntity0);
        saveMemberEntityList.add(memberEntity1);
        saveMemberEntityList.add(memberEntity2);

        Iterator<MemberEntity> memberEntityIterator = saveMemberEntityList.stream().iterator();

        found.stream()
                .forEach(memberEntity -> assertThat(memberEntity.getUid(), is(equalTo(memberEntityIterator.next().getUid()))));

    }



}
