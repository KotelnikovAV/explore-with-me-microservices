# Приложение EventLink.
## 1. Описание проекта.
### 1.1 Идея приложения.
Приложение - афиша. Приложение позволит пользователям делиться информацией об интересных событиях и находить компанию 
для участия в них.  
Свободное время — ценный ресурс. Ежедневно мы планируем, как его потратить — куда и с кем сходить. 
Сложнее всего в таком планировании поиск информации и переговоры. Нужно учесть много деталей: какие намечаются 
мероприятия, свободны ли в этот момент друзья, как всех пригласить и где собраться.  
### 1.2 Основная функциональность приложения.
Приложение состоит из публичного, приватного и административного API. Также приложение содержит в себе рекомендательный сервис.  

Основная функциональность публичного API:  
- просмотр списка всех опубликованных мероприятий в краткой форме;
- просмотр конкретного мероприятия в расширенной форме.

Основная функциональность приватного API для авторизованного пользователя:
- публикация и редактирование мероприятий;
- возможность подавать заявку на участие в мероприятии, а также ее редактирование до момента получения подтверждения;
- возможность подтверждать или отклонять входящие заявки на созданное им мероприятие;
- возможность ставить лайки посещенным мероприятиям, на основании которых формируется внешний рейтинг мероприятий 
и пользователей, опубликовавших их;
- возможность получения списка рекомендуемых мероприятий на основании взаимодействий его и на него похожих пользователей 
с другими мероприятиями.

Основная функциональность административного API:
- модерация опубликованных событий (подтверждение или отклонение публикации мероприятий).

Основная функциональность рекомендательного сервиса:
- сбор и агрегация действий авторизованных пользователей;
- получение списка рекомендуемых мероприятий для конкретного пользователя;
- предсказание действия, которое совершит конкретный пользователь над конкретным мероприятием.

### 1.3 Стек используемых технологий.
Основные технологии и инструменты, используемые в данном проекте:
- Java (Amazon Corretto 21.0.6);
- Maven;
- Spring Boot;
- Spring Cloud;
- PostgreSQL;
- Hibernate ORM;
- Docker;
- Kafka;
- REST;
- gRPC.

## 2. Инструкция по развертыванию проекта.
Для запуска приложения необходимо:
- склонировать проект: https://github.com/KotelnikovAV/java-plus-graduation.git;
- открыть проект с помощью IntelliJ IDEA и выполнить команду mvn package;
- запустить исполнение файла docker-compose.yml.  

После успешного исполнения файла docker-compose.yml приложение будет доступно на порту 8080.

## 3. Планы по доработке проекта.
В ближайшем будущем планируется доработка функциональности. Планируется:
- введение возможности авторизованным пользователям подписываться/добавлять в друзья других авторизованных пользователей;
- введение возможности авторизованным пользователям оставлять многоуровневые комментарии к опубликованным событиям;
- улучшение возможностей администрирования мероприятиями.

## 4. Техническая документация проекта.
Спецификация API в формате openapi: [el-main-service-spec.json](https://github.com/user-attachments/files/18718734/ewm-main-service-spec.json)
