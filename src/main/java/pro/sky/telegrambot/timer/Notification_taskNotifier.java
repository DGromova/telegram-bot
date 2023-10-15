package pro.sky.telegrambot.timer;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.repository.Notification_taskRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class Notification_taskNotifier {

    private final Notification_taskRepository notification_taskRepository;
    private final TelegramBot telegramBot;

    public Notification_taskNotifier(Notification_taskRepository notificationTaskRepository, TelegramBot telegramBot) {
        notification_taskRepository = notificationTaskRepository;
        this.telegramBot = telegramBot;
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void run() {
        LocalDateTime nowDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        notification_taskRepository.findNotificationTasksWithNowDataTime(nowDateTime).stream()
                .forEach(n -> {
                    telegramBot.execute(new SendMessage(n.getChatId(), n.getNotification()));
                    notification_taskRepository.delete(n);
                });
    }

}
