package com.example.pfairplayservice.jpa.repository;


import com.example.pfairplayservice.util.TestEntityGenerator;
import com.example.pfairplayservice.jpa.model.MemberEntity;
import com.example.pfairplayservice.jpa.model.MemberTeamEntity;
import com.example.pfairplayservice.jpa.model.TeamEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TeamRepositoryIntegrationTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MemberTeamRepository memberTeamRepository;

    @Test
    public void whenFindById_thenReturnTeamEntity() {

        // given
        MemberEntity memberEntity = TestEntityGenerator.generateMemberEntity();
        TeamEntity teamEntity = TestEntityGenerator.generateTeamEntity(memberEntity);

        memberRepository.save(memberEntity);
        teamRepository.save(teamEntity);

        // when
        Optional<TeamEntity> found = teamRepository.findById(teamEntity.getTid());

        // then
        assertThat(found.get().getTid(), is(equalTo(teamEntity.getTid())));

        // remove source
        memberRepository.delete(memberEntity);
        teamRepository.delete(teamEntity);

    }

    @Test
    public void whenFindByMemberTeamUid_thenReturnMemberEntityList() {

        // given
        MemberEntity memberEntity = TestEntityGenerator.generateMemberEntity();

        memberRepository.save(memberEntity);

        TeamEntity teamEntity0 = TestEntityGenerator.generateTeamEntity(memberEntity);
        TeamEntity teamEntity1 = TestEntityGenerator.generateTeamEntity(memberEntity);
        TeamEntity teamEntity2 = TestEntityGenerator.generateTeamEntity(memberEntity);

        teamRepository.save(teamEntity0);
        teamRepository.save(teamEntity1);
        teamRepository.save(teamEntity2);

        MemberTeamEntity memberTeamEntity0 = TestEntityGenerator.generateMemberTeamEntity(memberEntity.getUid(), teamEntity0.getTid());
        MemberTeamEntity memberTeamEntity1 = TestEntityGenerator.generateMemberTeamEntity(memberEntity.getUid(), teamEntity1.getTid());
        MemberTeamEntity memberTeamEntity2 = TestEntityGenerator.generateMemberTeamEntity(memberEntity.getUid(), teamEntity2.getTid());

        memberTeamRepository.save(memberTeamEntity0);
        memberTeamRepository.save(memberTeamEntity1);
        memberTeamRepository.save(memberTeamEntity2);

        List<TeamEntity> before = new ArrayList<>();
        before.add(teamEntity0);
        before.add(teamEntity1);
        before.add(teamEntity2);

        // when
        List<TeamEntity> after = teamRepository.findByMemberTeamIdUid(memberEntity.getUid());

        // then
        Iterator<TeamEntity> teamEntityIterator = before.stream().iterator();
        after.stream()
                .forEach(teamEntity -> assertThat(teamEntity.getTid(), is(equalTo(teamEntityIterator.next().getTid()))));

        // remove source
        memberRepository.delete(memberEntity);
        teamRepository.delete(teamEntity0);
        teamRepository.delete(teamEntity1);
        teamRepository.delete(teamEntity2);
        memberTeamRepository.delete(memberTeamEntity0);
        memberTeamRepository.delete(memberTeamEntity1);
        memberTeamRepository.delete(memberTeamEntity2);

    }

}
