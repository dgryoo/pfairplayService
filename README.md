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
![pfairplay](https://user-images.githubusercontent.com/59459120/131520629-9d3a0cac-d5cb-4d9a-ab22-0d47fe6197d8.png)


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
![pfairplay_table](https://user-images.githubusercontent.com/59459120/131523175-6d55ee6e-d55f-41dd-befa-4816160c0a64.png)

## Rest API 문서

