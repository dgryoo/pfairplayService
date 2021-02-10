### MemberRepositoryIntergrationTest

## 발생할 수 있는 상황?
    @Query(value = "SELECT * FROM test.member m WHERE m.uid in (SELECT mt.uid FROM test.member_team_list mt where mt.tid = :tid)", nativeQuery = true)
    List<MemberEntity> findByMemberTeamIdTid(@Param("tid") String tid);
- 해당 tid의 team이 없을 경우?? -> 404 NOTFOUND (ctrl)
- tid는 있지만 등록 된 멤버가 없는 경우? -> MemberEntity == Empty (repo)
- MemberTeamList에 있는 MemberEntity가 정상적으로 호출 되는지 <br>
선행작업으로 memberTeamList에 save()하는 메소드 작성