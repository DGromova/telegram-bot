package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.entity.Notification_task;

public interface Notification_taskRepository extends JpaRepository<Notification_task, Long> {

}
