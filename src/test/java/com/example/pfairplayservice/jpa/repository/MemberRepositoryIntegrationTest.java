package com.example.pfairplayservice.jpa.repository;


import com.example.pfairplayservice.jpa.model.MemberEntity;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class MemberRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MemberRepository memberRepository;

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

}
