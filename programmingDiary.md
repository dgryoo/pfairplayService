### Knowledge

 - 4 3 2 1 -> big endian
  
   1 2 3 4 -> little endian

 - RFC(Request for Comments) HTTP, TCP/IP 등 문서를 참고해야할 때 찾아보자
 
 - circulation reference 주의
 
## 타입안정성(Date를 year, month, day String format으로 받아서 재조합 할 때)
 
  - 버그가 발생할 확률이 높음, 타입 안정성
  - 여러가지 작업을 한다고 좋은 것이 아님. API는 그 역할에 맞게 최대한 안전하게
  - 어떠한 작업을 했을때 무엇을 잃는지 생각해야함
  - 참고 https://en.wikipedia.org/wiki/ISO_8601

### Shortcut

 - Ctrl + Shift + A -> Action
 
### Problem

## JPA IdentifierGenerationException (solved)

 - JPA에서  Repository.save()가 실행되기 전에 identifier는 먼저 생성 되어야 한다. mysql에서 sequence로 1씩 늘어나게 해놨는데 java에서 처리해야하나??
  원래는 1 2 3 ... 와 같이 sequence로 하려고 했음
  위와 같이 IdentifierGenerationException 발생
  google을 뒤져보니 밑과 같이 하면 된다고 함.
  @GeneratedValue(generator="system-uuid")
  @GenericGenerator(name="system-uuid", strategy = "uuid")
  
  그러나 실패. 알고보니 @GenericGenerator UID의 정의를 하는 것, @GeneratedValue는 어떤 GenericGenerator를 사용 할 것지를 설정 하는 어노테이션 이였음.
  두개를 넣고 어거지로 UID를 넣어 실행하니 UID가 암호화되어 데이터가 들어감.
  자동생성을 위에선 @GeneratedValue에 다음과 같이 strategy 옵션을 추가해 줘야함.
  @GeneratedValue(generator="member-uid", strategy = GenerationType.AUTO)
  AUTO, IDENTITY, SEQUENCE, TABLE 4가지 옵션이 존재하는데 AUTO로 할 경우 SEQUENCE로 설정되는 듯 함
  원래의 의도대로  1 2 3 ... 과 같이 사용하려 했다면 IDENTITIY로 설정 후 MYSQL에서 AUTO_INCREMENT로 하면 됨.
  
  Reference : https://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html
                      -> 2.6.6. Generated identifier values

  //@JsonIgnore() // JsonIgnoreProperties은 오타로 인해 런타임 에러가 날 수 있음 type system에 의해 보장됨
  
## team-post NullPointerException (solved)

 - 에러코드 Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is java.lang.NullPointerException] with root cause

## 'Embedded Id' attribute type should not be 'String' (solved)

 - composite primary key 를 위해 String uid, String tid에 @EmbeddedId 어노테이션을 사용했더니 다음과 같은 에러가 떳다.
  두개의 id 모두 uuid를 사용하고 있는 상황. IdClass를 두개다 만들어 주어야 하는건가?? 
  아니다 idClass는 하나만 만들면 된다 MemberTeamListEntity로
 - composite primary key는 @GeneratedValue 를 사용 할 수 없다고 한다. 그러면 현재 id들은 @GeneratedValue을 사용하고 있는데
  어떻게 해결해야 할까?

 - @GeneratedValue 삭제 시 문제점
  IdentifierGenerationException이 발생할 수 있다. 위에서 발생했던 문제인데 insert 쿼리를 날리기전에 미리 primary를 알고 있어야 한다.
  그러나 @GeneratedValue를 삭제하면 알 수 없다.
  키가 암호화 되지 않는다 @GenericGenerator(name = "member-uid", strategy = "uuid") 어노테이션으로 암호화 중이다.
  
## .ConverterNotFoundException (solved)
 
 - 기대했던 값은 tid 만 조회하는 것인데 jpql은 전부다 조회하는것으로 나옴.
select memberteam0_.tid as tid1_1_, memberteam0_.uid as uid2_1_ from member_team_list memberteam0_ where memberteam0_.uid=?
 
 - https://www.callicoder.com/hibernate-spring-boot-mysql-composite-primary-key-example/
 search : 2. Retrieving all employees of a particular company
 /*
 Spring Data JPA will automatically parse this method name
 and create a query from it
 */
 자동매핑 findByMemberTeamIdUid로하면 자동매핑 될 것으로 예상했으나 안됨
 @Query 사용


## ConversionFailedException (solved)
 @Query(value = "SELECT * FROM test.team t WHERE t.tid in (SELECT mt.tid FROM test.member_team_list mt where mt.uid = 'member1')", nativeQuery = true)
 ConversionFailedException: Failed to convert from type [java.lang.Object[]] to type [TeamEntity] for value '{team1, myground, 2020-01-01 09:00:00.0, 2020-01-01 09:00:00.0, testname1, member1}'; nested exception is org.springframework.core.convert.ConverterNotFoundException: No converter found capable of converting from type [java.lang.String] to type [TeamEntity]] with root cause
 데이터는 받아와지는데 TeamEntity로 Conversion이 안됨.
 
## RetrieveTeamListByUid, RetrieveMemberListByTid (solved)

 - Member, Team 각각 uid, tid로 복합키 MemberTeamId를 만들었다. 처음에는 MemberTeamEntity를 만들고 MemberTeamRepository를 만들어 그안에서
   각각 List<TeamEntity> findByMemberTeamIdUid(@Param("uid") String uid), List<MemberEntity> findByMemberTeamIdTid(@Param("tid") String tid) 메소드를 만들었다.
   그러나 public interface MemberTeamListRepository extends JpaRepository<MemberTeamEntity, MemberTeamId> 이 Repository에서는
   MemberTeamEntity, MemberTeamEntity를 상속을 받은 클래스만을 다룰 수 있다.
   해결방법 : MemberTeamListRepository에서 해당 메소드들을 만드는 것이 아닌 MemberRepositroy, TeamRepository에 각각 해당 Entity를 다루는 메소드 들을 만들어 주었다.
   
## Test case 작성시 에러 (solved)

 - create문에서 계속 에러가 나는것은 버전호환때문으로 추정 (버전을 낮추니 가능)
 
 - Entity에 notnull으로 설정된것은 필수적으로 넣어주어야함.

 - PersistenceException: org.hibernate.PersistentObjectException: detached entity passed to persist: MemberEntity
     @GeneratedValue(generator = "member-uid", strategy = GenerationType.AUTO)
     @GenericGenerator(name = "member-uid", strategy = "uuid")
     와 같은 어노테이션이 있기때문에 임의로 builder에서 uid를 지정해주었을 경우 에러발생.
   
## constructor MemberTeamId in class MemberTeamId cannot be applied to given types; (solved)
 - 기본생성자를 만들어주지않아서 발생

## JdbcSQLException (solved)

 - 내용 : Schema "TEST" not found; SQL statement: SELECT * FROM test.member m WHERE m.uid in (SELECT mt.uid FROM test.member_team_list mt where mt.tid = ?) [90079-197]
 - 해결방안 : 
 
## 생년월일 나이 표현 (solved)

 - 내용 : 멤버를 등록할때 생년월일을 받게되는데 멤버 조회시에 생년월일, 나이를 모두 보여주고싶음 생년으로 나이를 뽑을 수 있는데 이거는 back/front 누가 해야하는지?
        back이 해야한다면 Member에 age 컬럼을 추가하여 return 해야할듯함
 - 해결방안 : back에서는 정해진 type에 대해서만 받고 처리해야함 타입안정성 문제
    - 참고 https://en.wikipedia.org/wiki/ISO_8601

## 회원 정보수정 SpecialCharacterNotAllowException, LengthOverException, PatternSyntaxException (solved)
 - 내용 : 3가지 케이스를 에러처리 해야하는데 patternSyntaxException으로 통일 할지 아니면 3가지로 나눌지
 - 해결방안 : patternSyntaxException으로 통일, 재사용성에 초점을 두고 정규표현식으로 표현, 메세지로 각 케이스에 대한 표현을 해주자.
 
## 회원 정보수정 API method (solved)

 - 내용 : 정보수정을 할떄에 password, 주소, 포지션 등 을 수정할 수 있도록 할것인데 이러한 것들을 어떻게 처리할 것인지
        방법 1. requestbody로 전부 받은다음 각각의 속성에 대해 모두 처리해주는 api 생성
            장점 : repository에서 하나의 메소드만 생성해도 된다.
            단점 : 변경되지 않은 속성도 덮어쓰여짐
        방법 2. 각각의 속성에 대해 각각의 메소드를 만든다.
            장점 : 변경된 속성만 업데이트함
            단점 : 메소드를 여러번 호출해야함
        Q : 정보를 수정할때에 MemberEntity를 사용 할 것인지, 아니면 변경된 정보만 담을 수 있는 Entity를 추가적으로 생성해야하는지?
 - 해결방안 : 수정할 수 있는 모든 정보를 받고 바뀐것만 update하도록 수정 

## Json Date format (solved)
 - reference : https://en.wikipedia.org/wiki/ISO_8601
 
## Date Type (solved)

 - 내용 : LocalDateTime을 날짜형태로 통합했더니 다음과 같은 Exception 발생
  -> @Temporal should only be set on a java.util.Date or java.util.Calendar property
 - 방법 1 : Date or Calendar Type으로 날짜 자료형을 맞추되 다른 타입과 매핑을 통해 사용 (굳이 필요한가? 확인필요)
 - 해결방안 : 필요없음 그냥 Date 타입으로 받으면 됨
 
## member-put. Can not issue data manipulation statements with executeQuery(). (solved)
 - 내용 : 정부수정을 할때 다음과 같은 Error 발생
 - 해결방안 : DML을 할때에는 @Modify, @Transactional 어노테이션을 사용해 줘야함, 이러한 어노테이션이 없으면 기본으로 executeQuery()를 사용하게
            되는데 따로 결과값이 없어 오류가 발생함 DML은 void 함수
 - 추가정보 : 해당 어노테이션에는 @Transactional이 포함되어 있다고 하는데, 제외하고 실행하니 해당 어노테이션이 필요하다는 에러가 남
 
## Incompatible types: expected void but the lambda body is neither a statement expression nor a void-compatible block #6
- 내용 : 람다식으로 다음과 같이 했을때 에러가 남
- 해결방안? 임시방편? : while, Iterator 사용 
```
teamEntityList.forEach(teamEntity -> {
            if(teamEntity.getTeamLeadMember().getUid().equals(saveTeam.getTeamLeadMember().getUid())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        });
``` 

## team의 inner class member #7

- 내용 :  
- 해결방안 : 
    1. 팀의 TeamEntity의 필드를 MemberEntity -> leadMemberUid로 바꾸고 TeamRepository의 findById를 override해서 @Query로 사용 
        <br> team을 get 할때 teamLeadMember가 다 나오지 않을 것으로 예상됨.
    2. 팀은 팀대로 하고 TeamLeaderEntity를 새로 생성하여 매핑을 통해 사용
    3. 어차피 uid만 사용하니 member에서 uid만 있는 생성자를 만들어서 MemberEntity로 변환 (가능한지 해봐야함)
- 참고사항 
    - 팀등록시 teamLeadMember의 모든 정보를 받아야하는지 고려
    - 이미 등록된 팀은 팀의 이름, teamLeadMember의 uid로 체크
    
- 진행사항
    - 3번으로 우선 진행중, 약간 억지로 끼워 맞추는 느낌이 있긴 하지만 되긴 됨.
    - team 을 post 할때는 teamLeadMember의 uid만 받음.