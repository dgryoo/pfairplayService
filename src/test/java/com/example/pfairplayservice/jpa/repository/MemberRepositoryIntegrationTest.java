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
public class MemberRepositoryIntegrationTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MemberTeamRepository memberTeamRepository;

    @Test
    public void whenFindById_thenReturnMemberEntity() {

        // given
        MemberEntity memberEntity = TestEntityGenerator.generateMemberEntity();

        memberRepository.save(memberEntity);


        // when
        Optional<MemberEntity> found = memberRepository.findById(memberEntity.getUid());

        // then
        assertThat(found.get().getUid(), is(equalTo(memberEntity.getUid())));

        // remove source
        memberRepository.delete(memberEntity);

    }

    @Test
    public void whenFindByMemberTeamTid_thenReturnMemberEntityList() {

        // given
        MemberEntity memberEntity0 = TestEntityGenerator.generateMemberEntity();
        MemberEntity memberEntity1 = TestEntityGenerator.generateMemberEntity();
        MemberEntity memberEntity2 = TestEntityGenerator.generateMemberEntity();

        memberRepository.save(memberEntity0);
        memberRepository.save(memberEntity1);
        memberRepository.save(memberEntity2);

        TeamEntity teamEntity = TestEntityGenerator.generateTeamEntity(memberEntity0);

        teamRepository.save(teamEntity);

        MemberTeamEntity memberTeamEntity0 = TestEntityGenerator.generateMemberTeamEntity(memberEntity0.getUid(), teamEntity.getTid());
        MemberTeamEntity memberTeamEntity1 = TestEntityGenerator.generateMemberTeamEntity(memberEntity1.getUid(), teamEntity.getTid());
        MemberTeamEntity memberTeamEntity2 = TestEntityGenerator.generateMemberTeamEntity(memberEntity2.getUid(), teamEntity.getTid());

        memberTeamRepository.save(memberTeamEntity0);
        memberTeamRepository.save(memberTeamEntity1);
        memberTeamRepository.save(memberTeamEntity2);



        List<MemberEntity> before = new ArrayList<>();
        before.add(memberEntity0);
        before.add(memberEntity1);
        before.add(memberEntity2);

        // when
        List<MemberEntity> after = memberRepository.findByMemberTeamIdTid(teamEntity.getTid());

        // then
        Iterator<MemberEntity> memberEntityIterator = before.stream().iterator();
        after.stream()
                .forEach(memberEntity -> assertThat(memberEntity.getUid(), is(equalTo(memberEntityIterator.next().getUid()))));

        // remove source
        memberRepository.delete(memberEntity0);
        memberRepository.delete(memberEntity1);
        memberRepository.delete(memberEntity2);
        teamRepository.delete(teamEntity);
        memberTeamRepository.delete(memberTeamEntity0);
        memberTeamRepository.delete(memberTeamEntity1);
        memberTeamRepository.delete(memberTeamEntity2);


    }

}
