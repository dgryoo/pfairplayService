# PFairPlay Service

## 특정 멤버 조회 (Member)

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
|uid|String|유저식별자|
|id|String|유저 id|
|name|String|이름|
|birthday|Date|생년월일|
|address|String|주소 (법정동 단위까지만)|
|phoneNumber|String|핸드폰번호|
|preferPosition|String|선호포지션 <br> 0.NONE <br> 1. FW <br> 2. MF <br> 3. DF <br> 4. GK|
|level|int|실력 <br> 1 ~ 5 <br> 숫자가 클수록 높은 level <br> 0. 확인되지않음|
|phoneNumberDisclosureOption|String|핸드폰번호 공개 범위 <br> 0. ALL <br> 1.TEAM <br> 2.NOBODY|
|joinDate|Date|가입일|
|recentLoginDate|Date|최근 로그인일|

### status code

|code|ExceptionName|detail|
|---|---|---|
|200|-|조회 성공|
|404|-|해당 uid의 멤버가 없는 경우|
|500|-|서버오류로 인해 조회 실패|

## 멤버 가입 (Member)

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
|preferPosition|int|N|0|선호포지션 <br> 0.NONE <br> 1. FW <br> 2. MF <br> 3. DF <br> 4. GK|- 숫자|
|level|int|N|0|실력 <br> 5. 상 <br> 4. 중상<br> 3. 중<br> 2. 중하<br> 1. 하|- 숫자|
|phoneNumberDisclosureOption|int|N|0|핸드폰번호 공개 범위 <br> 0. ALL <br> 1.TEAM <br> 2.NOBODY|- 숫자|

### response body

응답 바디가 없습니다.

### status code

|statusCode|exceptionName|detail|
|---|---|---|
|201|-|멤버 생성 성공|-|
|400|PatternSyntaxException|특수문자가 허용되지 않는 경우|
|400|PatternSyntaxException|길이를 초과하는 경우|
|400|PatternSyntaxException|정규표현식에 맞지 않음|
|400|RequiredParamNotFoundException|내용이 null인 경우|
|409|-|해당 id(추후 주민등록번호 or 핸드폰 or 카카오 Auth로 변경)로 계정이 이미 존재할 경우|-|
|500|-|서버 오류로 인해 생성 실패|-|

## 멤버 정보수정 (Member)

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
|preferPosition|int|N|0|선호포지션 <br> 0.NONE <br> 1. FW <br> 2. MF <br> 3. DF <br> 4. GK|
|level|int|Y|-|실력 <br> 1 ~ 5 <br> 숫자가 클수록 높은 level <br> 0. 확인되지않음|
|phoneNumberDisclosureOption|int|Y|-|핸드폰번호 공개 범위 <br> 0. ALL <br> 1.TEAM <br> 2.NOBODY|

### response body

응답 바디가 없습니다.

### status code

|statusCode|exceptionName|detail|
|---|---|---|
|200|-|수정 성공|
|400|PatternSyntaxException|특수문자가 허용되지 않는 경우|
|400|PatternSyntaxException|길이를 초과하는 경우|
|400|PatternSyntaxException|정규표현식에 맞지 않음|
|404|-|해당 uid의 멤버가 없는 경우|
|500|-|서버 오류로 인해 수정 실패|

## 멤버 삭제 (Member)

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

## 특정 팀의 멤버 조회 (Member)

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

## 특정 팀 정보 조회 (Team)

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
|tid|String|팀 식별자|
|teamName|String|이름|
|activityAreaAddress|String|주 활동지역 주소 (구 단위까지)|
|foundDate|Date|팀 창단일|
|registrationDate|Date|팀 등록일|
|teamLeadMember|MemberForGet|대표멤버의 정보 <br> - 이름 <br> - 핸드폰번호|

### status code

|statusCode|exceptionName|detail|
|---|---|---|
|200|-|조회 성공|
|404|-|해당 tid의 팀이 없는 경우|
|500|-|서버오류로 인해 조회 실패|

## 팀 등록 (Team)

### API information

|method|requestURL|format|detail|
|---|---|---|---|
|POST|/team|json|팀 등록|

### request body

|requestVariableName|type|notnull|defaultValue|detail|constraint|
|---|---|---|---|---|---|
|teamName|String|Y|-|이름|- 길이 10 이하 <br> - 특수문자, 숫자 사용불가|
|teamLeadMemberUid|String|Y|-|대표멤버 식별자|-|
|activityAreaAddress|String|Y|-|주 활동지역 주소 (구 단위까지)|- 도시 시군구 구 읍면동 리 순서대로 <br> - 특수문자, 숫자 사용불가|
|foundDate|Date|N|-|팀 창단일|-|


### response body

응답 바디가 없습니다.

### status code

|statusCode|exceptionName|detail|
|---|---|---|
|201|-|팀 생성 성공|
|400|PatternSyntaxException|특수문자가 허용되지 않는 경우|
|400|PatternSyntaxException|길이를 초과하는 경우|
|400|PatternSyntaxException|정규표현식에 맞지 않음|
|400|RequiredParamNotFoundException|내용이 null인 경우|
|409|-|해당 대표자, 팀정보로 이미 팀이 존재할 경우|
|500|-|서버 오류로 인해 생성 실패|

## 팀 정보 수정 (Team)

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
|200|-|수정 성공|
|400|PatternSyntaxException|특수문자가 허용되지 않는 경우|
|400|PatternSyntaxException|길이를 초과하는 경우|
|400|PatternSyntaxException|정규표현식에 맞지 않음|
|404|-|해당 tid의 팀이 없는 경우|
|500|-|서버 오류로 인해 수정 실패|

## 팀 삭제 (Team)

### API information

|method|requestURL|format|detail|
|---|---|---|---|
|DELETE|/team/{tid}|json|팀 삭제|

### path variable

|requestVariableName|type|notnull|defaultValue|detail|constraint|
|---|---|---|---|---|---|
|tid|String|Y|-|팀 식별자|

### request param

|requestVariableName|type|notnull|defaultValue|detail|constraint|
|---|---|---|---|---|---|
|uid|String|Y|-|멤버 식별자 <br> 팀의 리더인지 확인을 위함|

### response body

응답 바디가 없습니다.

### status code

|statusCode|exceptionName|detail|
|---|---|---|
|200|-|팀 삭제 성공|
|401|-|팀의 리더가 아닌 다른 멤버가 팀을 삭제하려 하는 경우|
|404|-|해당 tid의 팀이 없는 경우|
|500|-|서버 오류로 인해 삭제 실패|

## 특정 멤버가 가입 된 팀 조회 (Team)

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

## 팀원등록 (MemberTeam)

### API information

|method|requestURL|format|detail|
|---|---|---|---|
|POST|/memberTeam|-|팀원 등록|

### request param

|requestVariableName|type|notnull|defaultValue|detail|constraint|
|---|---|---|---|---|---|
|uid|String|Y|-|Member identifier|-|
|tid|String|Y|-|team identifier|-|


### response body

응답 바디가 없습니다.

### status code

|statusCode|exceptionName|detail|
|---|---|---|
|201|-|팀원 등록 성공|
|400|RequiredParamNotFoundException|parameter가 null인 경우|
|404|-|해당 팀이나 멤버가 없는 경우|
|409|-|해당 팀원이 이미 등록되어 있는 경우|
|500|-|서버 오류로 인해 생성 실패|

## 팀원 삭제 (MemberTeam)

### API information

|method|requestURL|format|detail|
|---|---|---|---|
|DELETE|/memberTeam|-|팀원 삭제|

### request param

|requestVariableName|type|notnull|defaultValue|detail|constraint|
|---|---|---|---|---|---|
|uid|String|Y|-|Member identifier|-|
|tid|String|Y|-|team identifier|-|


### response body

응답 바디가 없습니다.

### status code

|statusCode|exceptionName|detail|
|---|---|---|
|200|-|팀원 삭제 성공|
|400|RequiredParamNotFoundException|parameter가 null인 경우|
|404|-|해당 팀이나 멤버가 없는 경우|
|500|-|서버 오류로 인해 삭제 실패|

## 팀 구해요 게시판 조회 (needTeamArticle)

### API information

|method|requestURL|format|detail|
|---|---|---|---|
|GET|/borad/needTeamArticle|json|팀을 구하는 멤버들의 게시판 조회|

### request body

요청변수가 없습니다.

### response body

|field|type|detail|
|---|---|---|
|summarizedNeedTeamArticles|List|article <br>- 글 식별자 int <br> - 제목 String<br> - 작성자 String<br> - 작성일 Date<br> - 조회수 int<br> - 상태 int <br> 정보를 가진 객체의 List를 반환 합니다.|

### status code

|statusCode|exceptionName|detail|
|---|---|---|
|200|-|조회 성공|
|204|-|해당 게시판에 글이 없음|
|500|-|서버오류로 인해 조회 실패|

## 팀 구해요 게시판 상세글 조회 (needTeamArticle)

### API information

|method|requestURL|format|detail|
|---|---|---|---|
|GET|/borad/needTeamArticle/{articleNo}|json|팀 구해요 게시판 게시글 상세 조회|

### path variable

|requestVariableName|type|notnull|defaultValue|detail|constraint|
|---|---|---|---|---|---|
|articleNo|int|Y|-|글 식별자|

### response body

|field|type|detail|
|---|---|---|
|articleNo|int|글 식별자|
|subject|String|제목|
|detail|String|글 내용|
|needPosition|int|필요 포지션 <br> 0.NONE <br> 1. FW <br> 2. MF <br> 3. DF <br> 4. GK|
|writeDate|Date|작성일|
|modifiedDate|Date|수정일|
|viewCount|int|조회수|
|status|int|상태 <br> 0. Ongoing <br> 1. Complete <br> 2. Hide|
|memberInfo|Member|- 이름 String<br> - 나이 int<br> - 주소 String<br> - 핸드폰번호(설정에 따라 적용) String <br> - 선호포지션 <br> - 실력 int<br> - 핸드폰번호노출범위 int <br>정보를 포함한 객체를 반환합니다.|


### status code
|statusCode|exceptionName|detail|
|---|---|---|
|200|-|조회 성공|
|404|-|해당 articleNo의 글이 없는 경우|
|500|-|서버오류로 인해 조회 실패|

## 팀 구해요 게시판 상세글 등록 (needTeamArticle)

|method|requestURL|format|detail|
|---|---|---|---|
|POST|/borad/needTeamArticle|json|글 등록|

### request body
|requestVariableName|type|notnull|defaultValue|detail|constraint|
|---|---|---|---|---|---|
|writeMember|Member|Y|-|uid를 포함 한 멤버 객체|-|
|subject|String|Y|-|글제목|- 20자 이하|
|detail|String|Y|-|글내용|- 255자 이하|
|needPosition|int|Y|-|필요포지션| 0.NONE <br> 1. FW <br> 2. MF <br> 3. DF <br> 4. GK|

### response body

응답 바디가 없습니다.

### status code

|statusCode|exceptionName|detail|
|---|---|---|
|201|-|글 생성 성공|-|
|400|PatternSyntaxException|길이를 초과하는 경우|
|400|PatternSyntaxException|정규표현식에 맞지 않음|
|400|RequiredParamNotFoundException|내용이 null인 경우|
|500|-|서버 오류로 인해 생성 실패|-|

## 팀 구해요 게시판 상세글 수정 (needTeamArticle)

### API information

|method|requestURL|format|detail|
|---|---|---|---|
|PUT|/borad/needTeamArticle/{articleNo}|json|글 수정|

### path variable

|requestVariableName|type|notnull|defaultValue|detail|constraint|
|---|---|---|---|---|---|
|articleNo|int|Y|-|글 식별자|

### request body
|requestVariableName|type|notnull|defaultValue|detail|constraint|
|---|---|---|---|---|---|
|writeMember|Member|Y|-|uid를 포함 한 멤버 객체|-|
|subject|String|Y|-|글제목|- 2 ~ 20자|
|detail|String|Y|-|글내용|- 1 ~ 255자|
|needPosition|int|Y|-|필요포지션|0.NONE <br> 1. FW <br> 2. MF <br> 3. DF <br> 4. GK|


### response body

응답 바디가 없습니다.

### status code

|statusCode|exceptionName|detail|
|---|---|---|
|200|-|수정 성공|
|400|PatternSyntaxException|길이를 초과하는 경우|
|400|PatternSyntaxException|정규표현식에 맞지 않음|
|404|-|해당 articleNo의 글이 없는 경우|
|500|-|서버 오류로 인해 수정 실패|

## 팀 구해요 게시판 상세글 삭제 (needTeamArticle)

### API information

|method|requestURL|format|detail|
|---|---|---|---|
|DELETE|/needTeamArticle/{articleNo}|-|글 삭제|

### path variable

|requestVariableName|type|notnull|defaultValue|detail|constraint|
|---|---|---|---|---|---|
|articleNo|int|Y|-|글 식별자|

### response body

응답 바디가 없습니다.

### status code

|statusCode|exceptionName|detail|
|---|---|---|
|200|-|글 삭제 성공|
|404|-|해당 articleNo의 글이 없는 경우|
|500|-|서버 오류로 인해 삭제 실패|

--------------------------------------------------------------------------

## 팀 구해요 게시판 조회 (needMemberArticle)

### API information

|method|requestURL|format|detail|
|---|---|---|---|
|GET|/borad/needMemberArticle|json|멤버를 구하는 팀들의 게시판 조회|

### request body

요청변수가 없습니다.

### response body

|field|type|detail|
|---|---|---|
|needMemberArticles|List|article <br>- 글 식별자 int <br> - 제목 String<br> - 작성팀 String<br> - 작성일 Date<br> - 조회수 int<br> 정보를 가진 객체의 List를 반환 합니다.|

### status code

|statusCode|exceptionName|detail|
|---|---|---|
|200|-|조회 성공|
|204|-|해당 게시판에 글이 없음|
|500|-|서버오류로 인해 조회 실패|

## 팀 구해요 게시판 상세글 조회 (needMemberArticle)

### API information

|method|requestURL|format|detail|
|---|---|---|---|
|GET|/borad/needMemberArticle/{articleNo}|json|멤버 구해요 게시판 게시글 상세 조회|

### path variable

|requestVariableName|type|notnull|defaultValue|detail|constraint|
|---|---|---|---|---|---|
|articleNo|int|Y|-|글 식별자|

### response body

|field|type|detail|
|---|---|---|
|articleNo|int|글 식별자|
|subject|String|글 제목|
|detail|String|글 내용|
|needPosition|int|필요 포지션 <br> 0.NONE <br> 1. FW <br> 2. MF <br> 3. DF <br> 4. GK|
|writeDate|Date|작성일|
|viewCount|int|조회수|
|teamInfo|Team|- 팀이름 String<br> - 활동지역 String<br> - 대표자 String<br>  - 대표자핸드폰번호 String <br>정보를 포함한 객체를 반환합니다.|


### status code
|statusCode|exceptionName|detail|
|---|---|---|
|200|-|조회 성공|
|404|-|해당 articleNo의 글이 없는 경우|
|500|-|서버오류로 인해 조회 실패|

## 팀 구해요 게시판 상세글 등록 (needMemberArticle)

|method|requestURL|format|detail|
|---|---|---|---|
|POST|/borad/needMemberArticle|json|글 등록|

### request body
|requestVariableName|type|notnull|defaultValue|detail|constraint|
|---|---|---|---|---|---|
|tid|String|Y|-|계정 id|- 255자 이하|
|subject|String|Y|-|글제목|- 20자 이하|
|detail|String|Y|-|글내용|- 255자 이하|
|needPosition|int|N|0|필요포지션| 0.NONE <br> 1. FW <br> 2. MF <br> 3. DF <br> 4. GK|

### response body

응답 바디가 없습니다.

### status code

|statusCode|exceptionName|detail|
|---|---|---|
|201|-|글 생성 성공|-|
|400|PatternSyntaxException|길이를 초과하는 경우|
|400|PatternSyntaxException|정규표현식에 맞지 않음|
|400|RequiredParamNotFoundException|내용이 null인 경우|
|500|-|서버 오류로 인해 생성 실패|-|

## 팀 구해요 게시판 상세글 수정 (needMemberArticle)

### API information

|method|requestURL|format|detail|
|---|---|---|---|
|PUT|/borad/needMemberArticle/{articleNo}|json|글 수정|

### path variable

|requestVariableName|type|notnull|defaultValue|detail|constraint|
|---|---|---|---|---|---|
|articleNo|int|Y|-|글 식별자|

### request body
|requestVariableName|type|notnull|defaultValue|detail|constraint|
|---|---|---|---|---|---|
|subject|String|Y|-|글제목|- 20자 이하|
|detail|String|Y|-|글내용|- 255자 이하|
|needPosition|int|N|0|필요포지션|0.NONE <br> 1. FW <br> 2. MF <br> 3. DF <br> 4. GK|


### response body

응답 바디가 없습니다.

### status code

|statusCode|exceptionName|detail|
|---|---|---|
|200|-|수정 성공|
|400|PatternSyntaxException|길이를 초과하는 경우|
|400|PatternSyntaxException|정규표현식에 맞지 않음|
|404|-|해당 articleNo의 글이 없는 경우|
|500|-|서버 오류로 인해 수정 실패|

## 팀 구해요 게시판 상세글 삭제 (needMemberArticle)

### API information

|method|requestURL|format|detail|
|---|---|---|---|
|DELETE|/needMemberArticle/{articleNo}|-|글 삭제|

### path variable

|requestVariableName|type|notnull|defaultValue|detail|constraint|
|---|---|---|---|---|---|
|articleNo|int|Y|-|글 식별자|

### response body

응답 바디가 없습니다.

### status code

|statusCode|exceptionName|detail|
|---|---|---|
|200|-|글 삭제 성공|
|404|-|해당 articleNo의 글이 없는 경우|
|500|-|서버 오류로 인해 삭제 실패|
