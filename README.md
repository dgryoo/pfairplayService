# PFairPlay Service

## 특정 멤버 조회

### API information

|method|requestURL|format|
|---|---|---|
|GET|/member/{uid}|json|

### path variable

|requestVariableName|type|notnull|defaultValue|detail|constraint|
|---|---|---|---|---|---|
|uid|String|Y|-|멤버 식별자|

### response body

|field|type|detail|
|---|---|---|
|registerdTeamNameList|List|등록된 teamName의 List를 반환합니다.|
|name|String|이름|
|birthday|Date|생년월일|
|address|String|주소 (법정동 단위까지만)|
|phoneNumber|String|핸드폰번호|
|preferPosition|String|선호포지션 <br> 1. ST <br> 2. CAM <br> 3. CM <br> 4. CDM <br> 5. WF <br> 6. WB <br> 7. CB <br> 8. GK|
|level|Integer|실력 <br> 1. 상 <br> 2. 중상<br> 3. 중<br> 4. 중하<br> 5. 하|
|phoneNumberDisclosureOption|Integer|핸드폰번호 공개 범위<br> 1 : 전체공개<br> 2 : 팀원에게만 공개<br> 3 : 공개안함|

### status code

|code|ExceptionName|detail|
|---|---|---|
|200|-|조회 성공|
|404|-|해당 uid의 멤버가 없는 경우|
|500|-|서버오류로 인해 조회 실패|

## 멤버 가입

### API information

|method|requestURL|format|detail|
|---|---|---|---|
|POST|/member|-|멤버 가입|

### request body
|requestVariableName|type|notnull|defaultValue|detail|constraint|
|---|---|---|---|---|---|
|id|String|Y|-|계정 id|- 길이 10 이하 <br> - 특수문자 사용불가|
|password|String|Y|-|계정 password|- 길이 10 이하 <br> - 특수문자 사용불가|
|name|String|Y|-|이름|- 길이 10 이하 <br> - 특수문자 사용불가|
|birthday|Date|Y|-|생년월일|- 숫자 <br> - 길이 8|
|address|String|Y|-|주소|- 도시 시군구 구 읍면동 리 순서대로 <br> - 특수문자, 숫자 사용불가|
|phoneNumber|String|Y|-|핸드폰번호|- 숫자 <br> -길이 11|
|preferPosition|String|N|-|선호포지션 <br> 1. ST <br> 2. CAM <br> 3. CM <br> 4. CDM <br> 5. WF <br> 6. WB <br> 7. CB <br> 8. GK|- 숫자|
|level|Integer|N|1|실력 <br> 5. 상 <br> 4. 중상<br> 3. 중<br> 2. 중하<br> 1. 하|- 숫자|
|phoneNumberDisclosureOption|int|N|1|핸드폰번호 공개 범위<br> 1 : 전체공개<br> 2 : 팀원에게만 공개<br> 3 : 공개안함|- 숫자|

### response body

응답 바디가 없습니다.

### status code

|statusCode|exceptionName|detail|
|---|---|---|
|201||멤버 생성 성공|-|
|400|PatternSyntaxException|특수문자가 허용되지 않는 경우|
|400|PatternSyntaxException|길이를 초과하는 경우|
|400|PatternSyntaxException|정규표현식에 맞지 않음|
|400|RequiredParamNotFoundException|내용이 null인 경우|
|409|-|해당 id(추후 주민등록번호 or 핸드폰 or 카카오 Auth로 변경)로 계정이 이미 존재할 경우|-|
|500|-|서버 오류로 인해 생성 실패|-|

## 멤버 정보수정 (TODO #2, 3)

### API information

|method|requestURL|format|detail|
|---|---|---|---|
|PUT|/member/{uid}|json|멤버의 정보 수정|

### path variable

|requestVariableName|type|notnull|defaultValue|detail|constraint|
|---|---|---|---|---|---|
|uid|String|Y|-|멤버 식별자|

### request body
|requestVariableName|type|notnull|defaultValue|detail|constraint|
|---|---|---|---|---|---|
|address|String|Y|-|주소 (법정동 단위까지만)|
|phoneNumber|String|Y|-|핸드폰번호|
|preferPosition|String|N|-|선호포지션 <br> 1. ST <br> 2. CAM <br> 3. CM <br> 4. CDM <br> 5. WF <br> 6. WB <br> 7. CB <br> 8. GK|
|level|String|Y|1|실력 <br> 1. 상 <br> 2. 중상<br> 3. 중<br> 4. 중하<br> 5. 하|
|phoneNumberDisclosureOption|int|Y|1|핸드폰번호 공개 범위<br> 1 : 전체공개<br> 2 : 팀원에게만 공개<br> 3 : 공개안함|

### response body

응답 바디가 없습니다.

### status code

|statusCode|exceptionName|detail|
|---|---|---|
|200||수정 성공|
|400|PatternSyntaxException|특수문자가 허용되지 않는 경우|
|400|PatternSyntaxException|길이를 초과하는 경우|
|400|PatternSyntaxException|정규표현식에 맞지 않음|
|404|-|해당 uid의 멤버가 없는 경우|
|500|-|서버 오류로 인해 수정 실패|

## 멤버 삭제

### API information

|method|requestURL|format|detail|
|---|---|---|---|
|DELETE|/member/{uid}|json|멤버 삭제|

### path variable

|requestVariableName|type|notnull|defaultValue|detail|constraint|
|---|---|---|---|---|---|
|uid|String|Y|-|멤버 식별자|

### response body

응답 바디가 없습니다.

### status code

|statusCode|exceptionName|detail|
|---|---|---|
|200|-|멤버 삭제 성공|
|404|-|해당 uid의 멤버가 없는 경우|
|500|-|서버 오류로 인해 삭제 실패|

## 특정 멤버가 가입 된 팀 조회

### API information

|method|requestURL|format|
|---|---|---|
|GET|/member/team/{uid}|json|

### path variable

|requestVariableName|type|notnull|defaultValue|detail|constraint|
|---|---|---|---|---|---|
|uid|String|Y|-|멤버 식별자|

### response body

|field|type|detail|
|---|---|---|
|teamList|List| - 팀이름 <br> - 대표자이름 <br> - 대표자핸드폰번호 <br> 포함된 멤버가 등록 된 팀 리스트를 반환합니다.|

### status code

|statusCode|exceptionName|detail|
|---|---|---|
|200|-|조회 성공|
|204|-|해당 유저가 등록한 팀이 없음|
|404|-|해당 uid의 멤버가 없는 경우|
|500|-|서버오류로 인해 조회 실패|

## 특정 팀 정보 조회

### API information

|method|requestURL|format|detail|
|---|---|---|---|
|GET|/team/{tid}|json|팀 정보 조회|

### path variable

|requestVariableName|type|notnull|defaultValue|detail|constraint|
|---|---|---|---|---|---|
|tid|String|Y|-|팀 식별자|

### response body

|field|type|detail|
|---|---|---|
|teamName|String|이름|
|activityAreaAddress|String|주 활동지역 주소 (구 단위까지)|
|foundDate|Date|팀 창단일|
|uid|String|대표멤버의 정보 <br> - 이름 <br> - 핸드폰번호|

### status code

|statusCode|exceptionName|detail|
|---|---|---|
|200|-|조회 성공|
|404|-|해당 tid의 팀이 없는 경우|
|500|-|서버오류로 인해 조회 실패|

## 팀 등록

### API information

|method|requestURL|format|detail|
|---|---|---|---|
|POST|/team|json|팀 등록|

### request body

|requestVariableName|type|notnull|defaultValue|detail|constraint|
|---|---|---|---|---|---|
|teamName|String|Y|-|이름|- 길이 10 이하 <br> - 특수문자, 숫자 사용불가|
|activityAreaAddress|String|Y|-|주 활동지역 주소 (구 단위까지)|- 도시 시군구 구 읍면동 리 순서대로 <br> - 특수문자, 숫자 사용불가|
|foundDate|Date|N|-|팀 창단일|- 숫자 <br> - 길이 8|


### response body

응답 바디가 없습니다.

### status code

|statusCode|exceptionName|detail|
|---|---|---|
|201||팀 생성 성공|
|400|PatternSyntaxException|특수문자가 허용되지 않는 경우|
|400|PatternSyntaxException|길이를 초과하는 경우|
|400|PatternSyntaxException|정규표현식에 맞지 않음|
|400|RequiredParamNotFoundException|내용이 null인 경우|
|409|-|해당 대표자, 팀정보로 이미 팀이 존재할 경우|
|500|-|서버 오류로 인해 생성 실패|

## 팀 정보 수정

### API information

|method|requestURL|format|detail|
|---|---|---|---|
|PUT|/team/{tid}|json|팀 정보 수정|

### path variable

|requestVariableName|type|notnull|defaultValue|detail|constraint|
|---|---|---|---|---|---|
|tid|String|Y|-|팀 식별자|

### request body

|requestVariableName|type|notnull|defaultValue|detail|constraint|
|---|---|---|---|---|---|
|teamName|String|Y|-|이름|- 길이 10 이하 <br> - 특수문자, 숫자 사용불가|
|activityAreaAddress|String|Y|-|주 활동지역 주소 (구 단위까지)|- 도시 시군구 구 읍면동 리 순서대로 <br> - 특수문자, 숫자 사용불가|
|foundDate|Date|N|-|팀 창단일|- 숫자 <br> - 길이 8|

### response body

응답 바디가 없습니다.

### status code

|statusCode|exceptionName|detail|
|---|---|---|
|200||수정 성공|
|400|PatternSyntaxException|특수문자가 허용되지 않는 경우|
|400|PatternSyntaxException|길이를 초과하는 경우|
|400|PatternSyntaxException|정규표현식에 맞지 않음|
|404|-|해당 tid의 팀이 없는 경우|
|500|-|서버 오류로 인해 수정 실패|

## 팀 삭제

### API information

|method|requestURL|format|detail|
|---|---|---|---|
|DELETE|/team/{tid}|json|팀 삭제|

### request body

|requestVariableName|type|notnull|defaultValue|detail|constraint|
|---|---|---|---|---|---|
|tid|String|Y|-|팀 식별자|

### response body

응답 바디가 없습니다.

### status code

|statusCode|exceptionName|detail|
|---|---|---|
|200|-|멤버 삭제 성공|
|404|-|해당 tid의 팀이 없는 경우|
|500|-|서버 오류로 인해 삭제 실패|

## 특정 팀의 멤버 조회

### API information

|method|requestURL|format|detail|
|---|---|---|---|
|GET|/team/member/{tid}|json|팀 정보 조회|

### path variable

|requestVariableName|type|notnull|defaultValue|detail|constraint|
|---|---|---|---|---|---|
|tid|String|Y|-|팀 식별자|

### response body

|field|type|detail|
|---|---|---|
|memberList|List| - 이름<br> - 나이 <br> - 주소<br> - 핸드폰번호<br> - 선호포지션<br> - 레벨 <br> 이 포함된 팀에 등록 된 멤버 리스트를 반환합니다.|

### status code

|statusCode|exceptionName|detail|
|---|---|---|
|200|-|조회 성공|
|404|-|해당 tid의 팀이 없는 경우|
|500|-|서버오류로 인해 조회 실패|

## 팀 구해요 게시판 조회

### API information

|method|requestURL|format|detail|
|---|---|---|---|
|GET|/borad/needteam|json|팀을 구하는 멤버들의 게시판 조회|

### request body

요청변수가 없습니다.

### response body

|field|type|detail|
|---|---|---|
|needteamArticles|List|article <br>- 게시글 번호 int <br> - 제목 String<br> - 이름 String<br> - 작성일 Date<br> - 조회수 int<br> 정보를 가진 객체의 List를 반환 합니다.|

### status code

|statusCode|exceptionName|detail|
|---|---|---|
|200|-|조회 성공|
|204|-|해당 게시판에 글이 없음|
|500|-|서버오류로 인해 조회 실패|

## 팀 구해요 게시판 상세글 조회

### API information

|method|requestURL|format|detail|
|---|---|---|---|
|GET|/borad/needteam/{articleNo}|json|팀 구해요 게시판 게시글 상세 조회|

### path variable

|requestVariableName|type|notnull|defaultValue|detail|constraint|
|---|---|---|---|---|---|
|articleNo|int|Y|-|게시글 번호|

### response body

|field|type|detail|
|---|---|---|
|articleNo|int|게시글 번호|
|subject|String|제목|
|contents|String|게시글 내용|
|writeDate|Date|작성일|
|count|int|조회수|
|memberInfo|Member|- 이름 String<br> - 나이 int<br> - 주소 String<br> - 핸드폰번호(설정에 따라 적용) String<br> - 선호포지션 String<br> - 실력 int<br>정보를 포함한 객체를 반환합니다.|


### status code
|statusCode|exceptionName|detail|
|---|---|---|
|200|-|조회 성공|
|404|-|해당 ariticleNo의 글이 없는 경우|
|500|-|서버오류로 인해 조회 실패|


## 팀원 구해요 게시판 조회

### API information

|method|requestURL|format|detail|
|---|---|---|---|
|GET|/borad/needmember|json|팀원을 구하는 팀의 게시판 조회|

### request body

요청변수가 없습니다.

### response body

|field|type|detail|
|---|---|---|
|needmemberArticles|List|- 게시글 번호 int <br> - 제목 String<br> - 팀이름 String<br> - 작성일 Date<br> - 조회수 int<br> 정보를 가진 객체의 List를 반환 합니다.|

### status code

|statusCode|exceptionName|detail|
|---|---|---|
|200|-|조회 성공|
|204|-|해당 게시판에 글이 없음|
|400|-|클라이언트의 잘못된 요청으로 인해 조회 실패|
|500|-|서버오류로 인해 조회 실패|

## 팀원 구해요 게시판 상세글 조회

### API information

|method|requestURL|format|detail|
|---|---|---|---|
|GET|/borad/needmember/{articleNo}|json|팀원 구해요 게시판 게시글 상세 조회|

### path variable

|requestVariableName|type|notnull|defaultValue|detail|constraint|
|---|---|---|---|---|---|
|articleNo|int|Y|-|게시글 번호|

### response body

|field|type|detail|
|---|---|---|
|articleNo|int|게시글 번호|
|subject|String|제목|
|contents|String|게시글 내용|
|writeDate|Date|작성일|
|count|int|조회수|
|teamInfo|Team| - 팀이름 String<br> - 팀 평균 나이 int<br> - 팀 주활동 주소 String<br> - 팀 대표 번호 String<br> - 팀이 필요한 포지션 String<br> - 팀 평균 레벨 int<br> 정보를 포함한 객체를 반환합니다.|

### status code
|statusCode|exceptionName|detail|
|---|---|---|
|200|-|조회 성공|
|400|-|클라이언트의 잘못된 요청으로 인해 조회 실패|
|404|-|해당 ariticleNo의 글이 없는 경우|
|500|-|서버오류로 인해 조회 실패|
