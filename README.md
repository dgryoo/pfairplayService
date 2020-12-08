# PFairPlay Service
--

## 1. API 기본정보
|메서드|요청URL|출력포맷|설명|매칭|
|---|---|---|---|---|
|POST|/members/register|json|멤버로 가입하기|1|

## 2. 요청변수

|요청변수명|타입|필수 여부|기본값|설명|매칭|
|---|---|---|---|---|---|
|name|String|Y|-|이름|1|
|birthday|Date|Y|-|생년월일|1
|address|String|Y|-|주소 (법정동 단위까지만 필요)|1|
|phoneNumber|String|Y|-|핸드폰번호|1
|preferPosition|String|N|-|선호포지션|1|
|grade|String|N|-|실력|1|

## 3. 출력결과
|필드|타입|설명|
|---|---|---|
|status|int|200, 400, 500 |
|mainUrl|String|메인페이지 URL|
|searchTeamUrl|String|팀 찾기 URL|
|myPageUrl|String|마이페이지 URL|

## 4. 에러코드

에러코드 정리할 예정

