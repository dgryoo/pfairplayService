# PFairPlay Service

## 멤버

### 1. API 기본정보

|메서드|요청URL|출력포맷|설명|순번|
|---|---|---|---|---|
|GET|/members/myPage|json|자신의 정보 조회|1|
|POST|/members/register|json|멤버 가입|2|
|PUT|/members/update/{UID}|json|자신의 정보 수정|3|
|DELETE|/members/delete/{UID}|json|멤버 삭제|4|

### 2. 요청변수

#### 2.1 마이페이지

|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|UID|String|Y|-|멤버 식별자|

#### 2.2 멤버 가입
|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|memberName|String|Y|-|이름|
|birthday|Date|Y|-|생년월일|
|address|String|Y|-|주소 (법정동 단위까지만)|
|phoneNumber|String|Y|-|핸드폰번호|
|preferPosition|String|N|-|선호포지션|
|level|String|Y|1|실력 (1~5)|
|phoneNumberDisclosureOption|int|Y|1|핸드폰번호 공개 범위<br> 1 : 전체공개<br> 2 : 팀원에게만 공개<br> 3 : 공개안함|

#### 2.3 자신의 정보 수정
|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|UID|String|Y|-|멤버 식별자|
|address|String|Y|-|주소 (법정동 단위까지만)|
|phoneNumber|String|Y|-|핸드폰번호|
|preferPosition|String|N|-|선호포지션|
|level|String|Y|1|실력 (1~5)|
|phoneNumberDisclosureOption|int|Y|1|핸드폰번호 공개 범위<br> 1 : 전체공개<br> 2 : 팀원에게만 공개<br> 3 : 공개안함|

#### 2.4 멤버 삭제

|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|UID|String|Y|-|멤버 식별자|


### 3. 출력결과

#### 3.1 마이페이지

|필드|타입|설명|
|---|---|---|
|teamName|String|등록된 팀이름|
|memberName|String|이름|
|birthday|Date|생년월일|
|address|String|주소 (법정동 단위까지만)|
|phoneNumber|String|핸드폰번호|
|preferPosition|String|-|선호포지션|
|level|String|실력 (1~5)|
|phoneNumberDisclosureOption|int|핸드폰번호 공개 범위<br> 1 : 전체공개<br> 2 : 팀원에게만 공개<br> 3 : 공개안함|

#### 3.2 멤버 가입

|필드|타입|설명|
|---|---|---|
|status|int|200 : 가입 성공, 500 : 가입 실패|
|mainPageUrl|String|메인페이지 URL|
|loginPageUrl|String|로그인페이지 URL|

#### 3.3 자신의 정보 수정

|필드|타입|설명|
|---|---|---|
|status|int|200 : 수정 성공, 500 : 수정 실패|
|myPageUrl|String|마이페이지 URL|

#### 3.4 멤버 삭제

|필드|타입|설명|
|---|---|---|
|status|int|200 : 삭제 성공, 500 : 삭제 실패|
|mainPageUrl|String|메인페이지 URL|

## 팀

### 1. API 기본정보

|메서드|요청URL|출력포맷|설명|순번|
|---|---|---|---|---|
|GET|/teams/myteam|json|나의 팀 정보 조회|1|
|POST|/teams/register|json|팀 등록|2|
|PUT|/teams/update/{TID}|json|팀 정보 수정|3|
|DELETE|/teams/delete/{TID}|json|팀 삭제|4|

### 2. 요청변수

#### 2.1 마이팀페이지

|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|TID|String|Y|-|팀 식별자|

#### 2.2 팀 등록
|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|teamName|String|Y|-|이름|
|activityAreaAddress|String|Y|-|주 활동지역 주소 (구 단위까지)|
|foundDate|Date|N|-|팀 창단일|
|UID|String|Y|-|대표자|

#### 2.3 팀 정보 수정
|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|TID|String|Y|-|팀 식별자|
|teamName|String|Y|-|이름|
|activityAreaAddress|String|Y|-|주 활동지역 주소 (구 단위까지)|
|foundDate|Date|N|-|팀 창단일|
|UID|String|Y|-|대표자|

#### 2.4 팀 삭제

|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|TID|String|Y|-|팀 식별자|


### 3. 출력결과

#### 3.1 마이팀페이지

|필드|타입|설명|
|---|---|---|
|teamName|String|이름|
|activityAreaAddress|String|주 활동지역 주소 (구 단위까지)|
|foundDate|Date|팀 창단일|
|UID|String|대표자|
|memberList|List|팀에 등록 된 멤버 리스트|

#### 3.2 팀 등록

|필드|타입|설명|
|---|---|---|
|status|int|200 : 등록 성공, 500 : 등록 실패|
|mainPageUrl|String|메인페이지 URL|
|myteamPageUrl|String|마이팀페이지 URL|

#### 3.3 팀 정보 수정

|필드|타입|설명|
|---|---|---|
|status|int|200 : 수정 성공, 500 : 수정 실패|
|myteamPageUrl|String|마이팀페이지 URL|

#### 3.4 팀 삭제

|필드|타입|설명|
|---|---|---|
|status|int|200 : 삭제 성공, 500 : 삭제 실패|
|mainPageUrl|String|메인페이지 URL|

## 팀을 찾아요 게시판

## 팀원을 찾아요 게시판

## 로그인
