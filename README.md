# PFairPlay Service

## 특정 멤버 조회

### API information

|메서드|요청URL|출력포맷|
|---|---|---|
|GET|/member/{UID}|json|

### path variable

|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|UID|String|Y|-|멤버 식별자|

### response body

|필드|타입|설명|
|---|---|---|
|registerdTeamNames|List|등록된 teamName의 List를 반환합니다.|
|name|String|이름|
|birthday|Date|생년월일|
|address|String|주소 (법정동 단위까지만)|
|phoneNumber|String|핸드폰번호|
|preferPosition|String|선호포지션 <br> 1. ST <br> 2. CAM <br> 3. CM <br> 4. CDM <br> 5. WF <br> 6. WB <br> 7. CB <br> 8. GK|
|level|String|실력 <br> 1. 상 <br> 2. 중상<br> 3. 중<br> 4. 중하<br> 5. 하|
|phoneNumberDisclosureOption|int|핸드폰번호 공개 범위<br> 1 : 전체공개<br> 2 : 팀원에게만 공개<br> 3 : 공개안함|

### status code

|code|설명|
|---|---|
|200|조회 성공|
|400|클라이언트의 잘못된 요청으로 인해 조회 실패|
|404|해당 UID의 멤버가 없는 경우|
|500|서버오류로 인해 조회 실패|

## 멤버 가입

### API information

|메서드|요청URL|출력포맷|설명|
|---|---|---|---|
|POST|/member|json|멤버 가입|

### request body
|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|name|String|Y|-|이름|
|birthday|Date|Y|-|생년월일|
|address|String|Y|-|주소 (법정동 단위까지만)|
|phoneNumber|String|Y|-|핸드폰번호|
|preferPosition|String|N|-|선호포지션 <br> 1. ST <br> 2. CAM <br> 3. CM <br> 4. CDM <br> 5. WF <br> 6. WB <br> 7. CB <br> 8. GK|
|level|integer|N|1|실력 <br> 5. 상 <br> 4. 중상<br> 3. 중<br> 2. 중하<br> 1. 하|
|phoneNumberDisclosureOption|int|N|1|핸드폰번호 공개 범위<br> 1 : 전체공개<br> 2 : 팀원에게만 공개<br> 3 : 공개안함|

### response body

응답 바디가 없습니다.

### status code

|code|설명|케이스|
|---|---|---|
|201|멤버 생성 성공|-|
|400|클라이언트의 잘못된 요청으로 인해 생성 실패|LengthOverException : 길이를 초과하는 경우 <br>RequiredParamNotFoundException : 내용이 null인 경우|
|409|해당 (주민등록번호 or 핸드폰 or 카카오 등)으로 계정이 이미 존재할 경우|-|
|500|서버 오류로 인해 생성 실패|-|

## 멤버 정보수정

### API information

|메서드|요청URL|출력포맷|설명|
|---|---|---|---|
|PUT|/member/{UID}|json|멤버의 정보 수정|

### path variable

|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|UID|String|Y|-|멤버 식별자|

### request body
|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|address|String|Y|-|주소 (법정동 단위까지만)|
|phoneNumber|String|Y|-|핸드폰번호|
|preferPosition|String|N|-|선호포지션 <br> 1. ST <br> 2. CAM <br> 3. CM <br> 4. CDM <br> 5. WF <br> 6. WB <br> 7. CB <br> 8. GK|
|level|String|Y|1|실력 <br> 1. 상 <br> 2. 중상<br> 3. 중<br> 4. 중하<br> 5. 하|
|phoneNumberDisclosureOption|int|Y|1|핸드폰번호 공개 범위<br> 1 : 전체공개<br> 2 : 팀원에게만 공개<br> 3 : 공개안함|

### response body

응답 바디가 없습니다.

### status code

|code|설명|
|---|---|
|200|수정 성공|
|400|클라이언트의 잘못된 요청으로 인해 수정 실패|
|404|해당 UID의 멤버가 없는 경우|
|500|서버 오류로 인해 수정 실패|

## 멤버 삭제

### API information

|메서드|요청URL|출력포맷|설명|
|---|---|---|---|
|DELETE|/member/{UID}|json|멤버 삭제|

### path variable

|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|UID|String|Y|-|멤버 식별자|

### response body

응답 바디가 없습니다.

### status code

|code|설명|
|---|---|
|200|멤버 삭제 성공|
|400|클라이언트의 잘못된 요청으로 인해 삭제 실패|
|404|해당 UID의 멤버가 없는 경우|
|500|서버 오류로 인해 삭제 실패|

## 특정 팀 정보 조회

### API information

|메서드|요청URL|출력포맷|설명|
|---|---|---|---|
|GET|/team/{TID}|json|팀 정보 조회|

### path variable

|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|TID|String|Y|-|팀 식별자|

### response body

|필드|타입|설명|
|---|---|---|
|teamName|String|이름|
|activityAreaAddress|String|주 활동지역 주소 (구 단위까지)|
|foundDate|Date|팀 창단일|
|UID|String|대표자|
|memberList|List| - 이름<br> - 나이 <br> - 주소<br> - 핸드폰번호<br> - 선호포지션<br> 이 포함된 팀에 등록 된 멤버 리스트를 반환합니다.|

### status code

|code|설명|
|---|---|
|200|조회 성공|
|400|클라이언트의 잘못된 요청으로 인해 조회 실패|
|404|해당 TID의 팀이 없는 경우|
|500|서버오류로 인해 조회 실패|

## 팀 등록

### API information

|메서드|요청URL|출력포맷|설명|
|---|---|---|---|
|POST|/team|json|팀 등록|

### request body

|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|teamName|String|Y|-|이름|
|activityAreaAddress|String|Y|-|주 활동지역 주소 (구 단위까지)|
|foundDate|Date|N|-|팀 창단일|
|UID|String|Y|-|대표자|

### response body

응답 바디가 없습니다.

### status code

|code|설명|
|---|---|
|201|팀 생성 성공|
|400|클라이언트의 잘못된 요청으로 인해 생성 실패|
|409|해당 대표자, 팀정보로 이미 팀이 존재할 경우|
|500|서버 오류로 인해 생성 실패|

## 팀 정보 수정

### API information

|메서드|요청URL|출력포맷|설명|
|---|---|---|---|
|PUT|/team/{TID}|json|팀 정보 수정|

### path variable

|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|TID|String|Y|-|팀 식별자|

### request body

|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|teamName|String|Y|-|이름|
|activityAreaAddress|String|Y|-|주 활동지역 주소 (구 단위까지)|
|foundDate|Date|N|-|팀 창단일|

### response body

응답 바디가 없습니다.

### status code

|code|설명|
|---|---|
|200|수정 성공|
|400|클라이언트의 잘못된 요청으로 인해 수정 실패|
|404|해당 TID의 팀이 없는 경우|
|500|서버 오류로 인해 수정 실패|

## 팀 삭제

### API information

|메서드|요청URL|출력포맷|설명|
|---|---|---|---|
|DELETE|/team/{TID}|json|팀 삭제|

### request body

|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|TID|String|Y|-|팀 식별자|

### response body

응답 바디가 없습니다.

### status code

|code|설명|
|---|---|
|200|멤버 삭제 성공|
|400|클라이언트의 잘못된 요청으로 인해 삭제 실패|
|404|해당 TID의 팀이 없는 경우|
|500|서버 오류로 인해 삭제 실패|

## 팀 구해요 게시판 조회

### API information

|메서드|요청URL|출력포맷|설명|
|---|---|---|---|
|GET|/borad/needteam|json|팀을 구하는 멤버들의 게시판 조회|

### request body

요청변수가 없습니다.

### response body

|필드|타입|설명|
|---|---|---|
|needteamArticles|List|- 게시글 번호 int <br> - 제목 String<br> - 이름 String<br> - 작성일 Date<br> - 조회수 int<br> 정보를 가진 객체의 List를 반환 합니다.|

### status code

|code|설명|
|---|---|
|200|조회 성공|
|204|해당 게시판에 글이 없음|
|400|클라이언트의 잘못된 요청으로 인해 조회 실패|
|500|서버오류로 인해 조회 실패|

## 팀 구해요 게시판 상세글 조회

### API information

|메서드|요청URL|출력포맷|설명|
|---|---|---|---|
|GET|/borad/needteam/{articleNo}|json|팀 구해요 게시판 게시글 상세 조회|

### path variable

|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|articleNo|int|Y|-|게시글 번호|

### response body

|필드|타입|설명|
|---|---|---|
|articleNo|int|게시글 번호|
|subject|String|제목|
|contents|String|게시글 내용|
|writeDate|Date|작성일|
|count|int|조회수|
|memberInfo|Member|- 이름 String<br> - 나이 int<br> - 주소 String<br> - 핸드폰번호(설정에 따라 적용) String<br> - 선호포지션 String<br> - 실력 int<br>정보를 포함한 객체를 반환합니다.|


### status code
|code|설명|
|---|---|
|200|조회 성공|
|400|클라이언트의 잘못된 요청으로 인해 조회 실패|
|404|해당 ariticleNo의 글이 없는 경우|
|500|서버오류로 인해 조회 실패|


## 팀원 구해요 게시판 조회

### API information

|메서드|요청URL|출력포맷|설명|
|---|---|---|---|
|GET|/borad/needmember|json|팀원을 구하는 팀의 게시판 조회|

### request body

요청변수가 없습니다.

### response body

|필드|타입|설명|
|---|---|---|
|needmemberArticles|List|- 게시글 번호 int <br> - 제목 String<br> - 팀이름 String<br> - 작성일 Date<br> - 조회수 int<br> 정보를 가진 객체의 List를 반환 합니다.|

### status code

|code|설명|
|---|---|
|200|조회 성공|
|204|해당 게시판에 글이 없음|
|400|클라이언트의 잘못된 요청으로 인해 조회 실패|
|500|서버오류로 인해 조회 실패|

## 팀원 구해요 게시판 상세글 조회

### API information

|메서드|요청URL|출력포맷|설명|
|---|---|---|---|
|GET|/borad/needmember/{articleNo}|json|팀원 구해요 게시판 게시글 상세 조회|

### path variable

|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|articleNo|int|Y|-|게시글 번호|

### response body

|필드|타입|설명|
|---|---|---|
|articleNo|int|게시글 번호|
|subject|String|제목|
|contents|String|게시글 내용|
|writeDate|Date|작성일|
|count|int|조회수|
|teamInfo|Team| - 팀이름 String<br> - 팀 평균 나이 int<br> - 팀 주활동 주소 String<br> - 팀 대표 번호 String<br> - 팀이 필요한 포지션 String<br> - 팀 평균 레벨 int<br> 정보를 포함한 객체를 반환합니다.|

### status code
|code|설명|
|---|---|
|200|조회 성공|
|400|클라이언트의 잘못된 요청으로 인해 조회 실패|
|404|해당 ariticleNo의 글이 없는 경우|
|500|서버오류로 인해 조회 실패|
