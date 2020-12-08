# PFairPlay Service


## 1. API 기본정보

### 1.1 멤버
|메서드|요청URL|출력포맷|설명|순번|
|---|---|---|---|---|
|GET|/members|json|전체 멤버 조회|1|
|GET|/members/{UID}|json|특정 멤버 조회|2|
|POST|/members/register|json|멤버 등록|3|
|PUT|/members/update/{UID}|json|멤버 정보 수정|4|
|DELETE|/members/delete/{UID}|json|멤버 삭제|5|

### 1.2 팀
|메서드|요청URL|출력포맷|설명|순번|
|---|---|---|---|---|
|GET|/teams|json|전체 팀 조회|1|
|GET|/teams/{TID}|json|특정팀 조회|2|
|POST|/teams/register|json|팀 등록|3|
|PUT|/teams/update/{TID}|json|팀 정보 수정|4|
|DELETE|/teams/delete/{TID}|json|팀 삭제|5|


## 2. 요청변수

### 2.1.1 전체 멤버 조회

|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|-|-|-|-|전체 멤버를 조회|

### 2.1.2 특정 멤버 조회

|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|UID|String|Y|-|특정 멤버를 조회|

### 2.1.3 멤버 등록
|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|memberName|String|Y|-|이름|
|birthday|Date|Y|-|생년월일|
|address|String|Y|-|주소 (법정동 단위까지만)|
|phoneNumber|String|Y|-|핸드폰번호|
|preferPosition|String|N|-|선호포지션|
|level|String|Y|1|실력 (1~5)|
|phoneNumberDisclosureOption|int|Y|1|핸드폰번호 공개 범위<br> 1 : 전체공개<br> 2 : 팀원에게만 공개<br> 3 : 공개안함|

### 2.1.4 멤버 정보 수정

|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|UID|String|Y|-|멤버 식별자|
|address|String|Y|-|주소 (법정동 단위까지만)|
|phoneNumber|String|Y|-|핸드폰번호|
|preferPosition|String|N|-|선호포지션|
|level|String|Y|1|실력 (1~5)|
|phoneNumberDisclosureOption|int|Y|1|핸드폰번호 공개 범위<br> 1 : 전체공개<br> 2 : 팀원에게만 공개<br> 3 : 공개안함|

### 2.1.5 멤버 삭제

|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|UID|String|Y|-|멤버 식별자|


### 2.2.1 전체 팀 조회

|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|-|-|-|-|전체 팀을 보여줌|

### 2.2.2 특정 팀 조회

|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|UID|String|Y|-|특정 팀을 조회|

### 2.2.3 팀 등록
|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|teamName|String|Y|-|이름|
|activityAreaAddress|String|Y|-|주 활동지역 주소 (구 단위까지)|
|foundDate|Date|N|-|팀 창단일|
|UID|String|Y|-|대표자|

### 2.1.4 팀 정보 수정

|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|TID|String|Y|-|팀 식별자|
|teamName|String|Y|-|이름|
|activityAreaAddress|String|Y|-|주 활동지역 주소 (구 단위까지)|
|foundDate|Date|N|-|팀 창단일|
|UID|String|Y|-|대표자|

### 2.1.5 팀 삭제

|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|TID|String|Y|-|팀 식별자|

## 3. 출력결과

### 3.1.3
|필드|타입|설명|
|---|---|---|
|status|int|200, 400, 500 |
|mainUrl|String|메인페이지 URL|
|searchTeamUrl|String|팀 찾기 URL|
|myPageUrl|String|마이페이지 URL|

## 4. 에러코드


