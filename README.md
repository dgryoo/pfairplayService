# PFairPlay Service

## 멤버 마이페이지

### API 기본정보
|메서드|요청URL|출력포맷|
|---|---|---|
|GET|/members/myPage|json|

### 요청변수
 
|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|UID|String|Y|-|멤버 식별자|

### 출력결과

|필드|타입|설명|
|---|---|---|
|teamName|String|등록된 팀이름|
|memberName|String|이름|
|birthday|Date|생년월일|
|address|String|주소 (법정동 단위까지만)|
|phoneNumber|String|핸드폰번호|
|preferPosition|String|선호포지션|
|level|String|실력 (1~5)|
|phoneNumberDisclosureOption|int|핸드폰번호 공개 범위<br> 1 : 전체공개<br> 2 : 팀원에게만 공개<br> 3 : 공개안함|

## 멤버 가입

### API 기본정보

|메서드|요청URL|출력포맷|설명|
|---|---|---|---|
|POST|/members/register|json|멤버 가입|

### 요청변수
|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|memberName|String|Y|-|이름|
|birthday|Date|Y|-|생년월일|
|address|String|Y|-|주소 (법정동 단위까지만)|
|phoneNumber|String|Y|-|핸드폰번호|
|preferPosition|String|N|-|선호포지션|
|level|String|Y|1|실력 (1~5)|
|phoneNumberDisclosureOption|int|Y|1|핸드폰번호 공개 범위<br> 1 : 전체공개<br> 2 : 팀원에게만 공개<br> 3 : 공개안함|

### 출력 결과

|필드|타입|설명|
|---|---|---|
|status|int|200 : 가입 성공, 500 : 가입 실패|
|mainPageUrl|String|메인페이지 URL|
|loginPageUrl|String|로그인페이지 URL|

## 멤버 정보수정

### API 기본정보

|메서드|요청URL|출력포맷|설명|
|---|---|---|---|
|PUT|/members/update/{UID}|json|자신의 정보 수정|

### 요청 변수
|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|UID|String|Y|-|멤버 식별자|
|address|String|Y|-|주소 (법정동 단위까지만)|
|phoneNumber|String|Y|-|핸드폰번호|
|preferPosition|String|N|-|선호포지션|
|level|String|Y|1|실력 (1~5)|
|phoneNumberDisclosureOption|int|Y|1|핸드폰번호 공개 범위<br> 1 : 전체공개<br> 2 : 팀원에게만 공개<br> 3 : 공개안함|

### 출력 결과

|필드|타입|설명|
|---|---|---|
|status|int|200 : 수정 성공, 500 : 수정 실패|
|myPageUrl|String|마이페이지 URL|

## 멤버 삭제

### API 기본정보

|메서드|요청URL|출력포맷|설명|
|---|---|---|---|
|DELETE|/members/delete/{UID}|json|멤버 삭제|

### 요청 변수

|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|UID|String|Y|-|멤버 식별자|

### 출력 결과

|필드|타입|설명|
|---|---|---|
|status|int|200 : 삭제 성공, 500 : 삭제 실패|
|mainPageUrl|String|메인페이지 URL|

## 팀 마이페이지

### API 기본정보

|메서드|요청URL|출력포맷|설명|
|---|---|---|---|
|GET|/teams/myteam|json|나의 팀 정보 조회|

### 요청 변수

|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|TID|String|Y|-|팀 식별자|

### 출력 결과

|필드|타입|설명|
|---|---|---|
|teamName|String|이름|
|activityAreaAddress|String|주 활동지역 주소 (구 단위까지)|
|foundDate|Date|팀 창단일|
|UID|String|대표자|
|memberList|List|팀에 등록 된 멤버 리스트|

## 팀 등록

### API 기본정보

|메서드|요청URL|출력포맷|설명|
|---|---|---|---|
|POST|/teams/register|json|팀 등록|

### 요청 변수

|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|teamName|String|Y|-|이름|
|activityAreaAddress|String|Y|-|주 활동지역 주소 (구 단위까지)|
|foundDate|Date|N|-|팀 창단일|
|UID|String|Y|-|대표자|

### 출력 결과

|필드|타입|설명|
|---|---|---|
|status|int|200 : 등록 성공, 500 : 등록 실패|
|mainPageUrl|String|메인페이지 URL|
|myteamPageUrl|String|마이팀페이지 URL|

## 팀 정보 수정

### API 기본정보

|메서드|요청URL|출력포맷|설명|
|---|---|---|---|
|PUT|/teams/update/{TID}|json|팀 정보 수정|

### 요청 변수

|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|TID|String|Y|-|팀 식별자|
|teamName|String|Y|-|이름|
|activityAreaAddress|String|Y|-|주 활동지역 주소 (구 단위까지)|
|foundDate|Date|N|-|팀 창단일|
|UID|String|Y|-|대표자|

### 출력 결과

|필드|타입|설명|
|---|---|---|
|status|int|200 : 수정 성공, 500 : 수정 실패|
|myteamPageUrl|String|마이팀페이지 URL|

## 팀 삭제

### API 기본정보

|메서드|요청URL|출력포맷|설명|
|---|---|---|---|
|DELETE|/teams/delete/{TID}|json|팀 삭제|

### 요청 변수

|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|TID|String|Y|-|팀 식별자|

### 출력 결과

|필드|타입|설명|
|---|---|---|
|status|int|200 : 삭제 성공, 500 : 삭제 실패|
|mainPageUrl|String|메인페이지 URL|

## 팀 구해요 게시판 조회

### API 기본정보

|메서드|요청URL|출력포맷|설명|
|---|---|---|---|
|GET|/teams/borad/needteam|json|팀을 구하는 멤버들의 게시판 조회|

### 요청 변수

|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|boardID|String|Y|-|게시판 식별자|

### 출력 결과

|필드|타입|설명|
|---|---|---|
|articleNo|int|게시글 번호|
|subject|String|제목|
|writeMember|String|작성한 멤버 이름|
|writeDate|Date|작성일|
|count|int|조회수|

## 팀 구해요 게시판 상세글 조회

### API 기본정보

|메서드|요청URL|출력포맷|설명|
|---|---|---|---|
|GET|/teams/borad/needteam/{articleNo}|json|팀 구해요 게시판 게시글 상세 조회|

### 요청 변수

|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|boardID|String|Y|-|게시판 식별자|
|articleNo|int|Y|-|게시글 번호|

### 출력 결과

|필드|타입|설명|
|---|---|---|
|articleNo|int|게시글 번호|
|subject|String|제목|
|contents|String|게시글 내용|
|writeDate|Date|작성일|
|count|int|조회수|
|memberName|String|작성자 이름|
|age|Date|작성자 나이|
|address|String|작성자 주소 (법정동 단위까지만)|
|phoneNumber|String|작성자 핸드폰번호 작성자의 설정에 따라 공개 or 비공개 가능|
|preferPosition|String|작성자 선호포지션|
|level|String|작성자 실력 (1~5)|

## 팀원 구해요 게시판 조회

### API 기본정보

|메서드|요청URL|출력포맷|설명|
|---|---|---|---|
|GET|/teams/borad/needmember|json|팀원을 구하는 팀의 게시판 조회|

### 요청 변수

|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|boardID|String|Y|-|게시판 식별자|

### 출력 결과

|필드|타입|설명|
|---|---|---|
|articleNo|int|게시글 번호|
|subject|String|제목|
|writeTeam|String|작성한 팀 이름|
|writeDate|Date|작성일|
|count|int|조회수|

## 팀원 구해요 게시판 상세글 조회

### API 기본정보

|메서드|요청URL|출력포맷|설명|
|---|---|---|---|
|GET|/teams/borad/needtmember/{articleNo}|json|팀원 구해요 게시판 게시글 상세 조회|

### 요청 변수

|요청변수명|타입|필수 여부|기본값|설명|
|---|---|---|---|---|
|boardID|String|Y|-|게시판 식별자|
|articleNo|int|Y|-|게시글 번호|

### 출력 결과

|필드|타입|설명|
|---|---|---|
|articleNo|int|게시글 번호|
|subject|String|제목|
|contents|String|게시글 내용|
|writeDate|Date|작성일|
|count|int|조회수|
|teamName|String|작성팀 이름|
|AverageAge|Date|팀 평균 나이|
|activityAreaAddress|String|팀 주 활동 주소 (법정동 단위까지만)|
|phoneNumber|String|팀 대표 번호|
|needPosition|String|팀이 필요한 포지션|
|teamAverageLevel|double|팀 평균 레벨|


