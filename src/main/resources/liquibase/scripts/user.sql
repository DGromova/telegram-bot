-- liquibase formatted sql

-- changeset daria:1
-- preconditions onFail:MARK_RAN
--preconditions not tableExists tableName:Notification_task
CREATE TABLE Notification_task (
    id BIGSERIAL PRIMARY KEY,
    chatId BIGSERIAL NOT NULL,
    notification TEXT NOT NULL,
    dataTime TIMESTAMP NOT NULL
);