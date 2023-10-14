package pro.sky.telegrambot.mapper;

import org.springframework.stereotype.Component;
import pro.sky.telegrambot.dto.Notification_taskDtoIn;
import pro.sky.telegrambot.dto.Notification_taskDtoOut;
import pro.sky.telegrambot.entity.Notification_task;
import pro.sky.telegrambot.repository.Notification_taskRepository;


@Component
public class Notification_taskMapper {

    private final Notification_taskRepository notification_taskRepository;


    public Notification_taskMapper(Notification_taskRepository notificationTaskRepository) {
        notification_taskRepository = notificationTaskRepository;
    }

    public Notification_taskDtoOut toDto(Notification_task notification_task) {
        Notification_taskDtoOut notification_taskDtoOut = new Notification_taskDtoOut();
        notification_taskDtoOut.setId(notification_task.getId());
        notification_taskDtoOut.setChatId(notification_task.getChatId());
        notification_taskDtoOut.setNotification(notification_task.getNotification());
        notification_taskDtoOut.setDateTime(notification_task.getDateTime());
        return notification_taskDtoOut;
    }

    public Notification_task toEntity(Notification_taskDtoIn notification_taskDtoIn) {
        Notification_task notification_task = new Notification_task();
        notification_task.setChatId(notification_taskDtoIn.getChatId());
        notification_task.setNotification(notification_taskDtoIn.getNotification());
        notification_task.setDateTime(notification_taskDtoIn.getDateTime());
        return notification_task;
    }

}
