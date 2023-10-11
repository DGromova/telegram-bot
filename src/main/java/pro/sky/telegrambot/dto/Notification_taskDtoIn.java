package pro.sky.telegrambot.dto;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.Objects;

public class Notification_taskDtoIn {

    private Long chatId;
    private String notification;
    private LocalDateTime dateTime;

    public Notification_taskDtoIn(Long chatId, String notification, LocalDateTime dateTime) {
        this.chatId = chatId;
        this.notification = notification;
        this.dateTime = dateTime;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification_taskDtoIn that = (Notification_taskDtoIn) o;
        return Objects.equals(chatId, that.chatId) && Objects.equals(notification, that.notification) && Objects.equals(dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, notification, dateTime);
    }

    @Override
    public String toString() {
        return "Notification_taskDtoIn{" +
                "chatId=" + chatId +
                ", notification='" + notification + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }

}
