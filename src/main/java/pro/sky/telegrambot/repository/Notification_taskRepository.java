package pro.sky.telegrambot.repository;

import liquibase.pro.packaged.L;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pro.sky.telegrambot.entity.Notification_task;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public interface Notification_taskRepository extends JpaRepository<Notification_task, Long> {

    @Query(value = "SELECT * FROM notification_task WHERE dataTime = :nowDataTime", nativeQuery = true)
    List <Notification_task> findNotificationTasksWithNowDataTime(@Param("nowDataTime") LocalDateTime nowDataTime);

}
