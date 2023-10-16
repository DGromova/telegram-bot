-- liquibase formatted sql

-- changeset daria:1
-- preconditions onFail:MARK_RAN
-- preconditions not tableExists tableName:notification_task
CREATE TABLE notification_task (
    id BIGSERIAL PRIMARY KEY,
    chatId BIGSERIAL NOT NULL,
    notification TEXT NOT NULL,
    dataTime TIMESTAMP NOT NULL
);
