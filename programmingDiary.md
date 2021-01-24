# Knowledge



## ETC

 - 4 3 2 1 -> big endian
  
   1 2 3 4 -> little endian

 - RFC(Request for Comments) HTTP, TCP/IP 등 문서를 참고해야할 때 찾아보자


## Shortcut

 - Ctrl + Shift + A -> Action

## JPA IdentifierGenerationException

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
  
## team-post NullPointerException

 - 에러코드 Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is java.lang.NullPointerException] with root cause

## 'Embedded Id' attribute type should not be 'String'

 - composite primary key 를 위해 String uid, String tid에 @EmbeddedId 어노테이션을 사용했더니 다음과 같은 에러가 떳다.
  두개의 id 모두 uuid를 사용하고 있는 상황. IdClass를 두개다 만들어 주어야 하는건가?? 
  아니다 idClass는 하나만 만들면 된다 MemberTeamListEntity로
 - composite primary key는 @GeneratedValue 를 사용 할 수 없다고 한다. 그러면 현재 id들은 @GeneratedValue을 사용하고 있는데
  어떻게 해결해야 할까?

 - @GeneratedValue 삭제 시 문제점
  IdentifierGenerationException이 발생할 수 있다. 위에서 발생했던 문제인데 insert 쿼리를 날리기전에 미리 primary를 알고 있어야 한다.
  그러나 @GeneratedValue를 삭제하면 알 수 없다.
  키가 암호화 되지 않는다 @GenericGenerator(name = "member-uid", strategy = "uuid") 어노테이션으로 암호화 중이다.
  
## .ConverterNotFoundException
 
 - 기대했던 값은 tid 만 조회하는 것인데 jpql은 전부다 조회하는것으로 나옴.
select memberteam0_.tid as tid1_1_, memberteam0_.uid as uid2_1_ from member_team_list memberteam0_ where memberteam0_.uid=?
 
 - https://www.callicoder.com/hibernate-spring-boot-jpa-composite-primary-key-example/
 search : 2. Retrieving all employees of a particular company
 /*
 Spring Data JPA will automatically parse this method name
 and create a query from it
 */
 자동매핑 findByMemberTeamIdUid로하면 자동매핑 될 것으로 예상했으나 안됨
 @Query 사용


## ConversionFailedException
 @Query(value = "SELECT * FROM test.team t WHERE t.tid in (SELECT mt.tid FROM test.member_team_list mt where mt.uid = 'member1')", nativeQuery = true)
 ConversionFailedException: Failed to convert from type [java.lang.Object[]] to type [com.example.pfairplayservice.jpa.model.TeamEntity] for value '{team1, myground, 2020-01-01 09:00:00.0, 2020-01-01 09:00:00.0, testname1, member1}'; nested exception is org.springframework.core.convert.ConverterNotFoundException: No converter found capable of converting from type [java.lang.String] to type [com.example.pfairplayservice.jpa.model.TeamEntity]] with root cause
 데이터는 받아와지는데 TeamEntity로 Conversion이 안됨.
 
## RetrieveTeamListByUid, RetrieveMemberListByTid

 - Member, Team 각각 uid, tid로 복합키 MemberTeamId를 만들었다. 처음에는 MemberTeamEntity를 만들고 MemberTeamRepository를 만들어 그안에서
   각각 List<TeamEntity> findByMemberTeamIdUid(@Param("uid") String uid), List<MemberEntity> findByMemberTeamIdTid(@Param("tid") String tid) 메소드를 만들었다.
   그러나 public interface MemberTeamListRepository extends JpaRepository<MemberTeamEntity, MemberTeamId> 이 Repository에서는
   MemberTeamEntity, MemberTeamEntity를 상속을 받은 클래스만을 다룰 수 있다.
   해결방법 : MemberTeamListRepository에서 해당 메소드들을 만드는 것이 아닌 MemberRepositroy, TeamRepository에 각각 해당 Entity를 다루는 메소드 들을 만들어 주었다.
   
 
