-- liquibase formatted sql

-- changeset notifications:1
-- comment: создание таблицы для отправки уведомлений
CREATE TABLE notification_task
(
    id        SERIAL PRIMARY KEY,
    chat_id   BIGINT    NOT NULL,
    message   TEXT      NOT NULL,
    date_time TIMESTAMP NOT NULL
);