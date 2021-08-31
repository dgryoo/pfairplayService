# Soccer Team Matching Platform

## 개발환경
* IntelliJ
* Postman
* Mysql WorkBench
* GitHub

## 사용 기술
### 백엔드
#### Spring boot
* JAVA 8
* Spring Boot Web
* spring Boot Validation
* spring Boot Actuator
* spring Boot Jpa
* spring Boot Cassandra
* spring Boot Redis
* spring kafka

#### Build tool
* Maven

#### Database
* Mysql
* Redis
* Cassandra

#### Message Queue
* Kafka

## 주요 키워드
* REST API
* JPA
* Git version control
* Message Queue
* Event Driven Architecture
* Querydsl
* Nosql

## 시스템 구조
![pfairplay](https://user-images.githubusercontent.com/59459120/131518287-9fc1ae58-a688-4814-b465-13489db3e7e4.png)

##  API Server (Producer)
* [API Server](https://github.com/dgryoo/pfairplayService/tree/main/app/api-server)

##  Counter Server (Consumer)
* [Counter Server](https://github.com/dgryoo/pfairplayService/tree/main/app/counter-server)

##  Storage Module
* [Mysql](https://github.com/dgryoo/pfairplayService/tree/main/storage/mysql)
* [Cassandra](https://github.com/dgryoo/pfairplayService/tree/main/storage/cassandra)
* [Redis](https://github.com/dgryoo/pfairplayService/tree/main/storage/redis)
* [Kafka](https://github.com/dgryoo/pfairplayService/tree/main/storage/kafka)

## Database design
![pfairplay_table](https://user-images.githubusercontent.com/59459120/131504636-0e86a080-8933-4915-b902-b342d1c3fe99.png)

## Rest API 문서

