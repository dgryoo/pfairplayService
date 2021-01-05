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

 