package pro.sky.telegrambot.entity;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "notification_task")
public class NotificationTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "chatid")
    private Long chatId;

    @Column(length = 4096, nullable = false)
    private String notification;

    @Column(name = "datatime", nullable = false)
    private LocalDateTime dateTime;

    public NotificationTask(Long chatId, String notification, LocalDateTime dateTime) {
        this.chatId = chatId;
        this.notification = notification;
        this.dateTime = dateTime;
    }

    public NotificationTask() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

}
