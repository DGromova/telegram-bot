package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.dto.Notification_taskDtoIn;
import pro.sky.telegrambot.mapper.Notification_taskMapper;
import pro.sky.telegrambot.repository.Notification_taskRepository;

import javax.annotation.PostConstruct;
import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    private final Notification_taskMapper notification_taskMapper;
    private final Notification_taskRepository notification_taskRepository;

    public TelegramBotUpdatesListener(Notification_taskMapper notificationTaskMapper, Notification_taskRepository notificationTaskRepository) {
        notification_taskMapper = notificationTaskMapper;
        notification_taskRepository = notificationTaskRepository;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            // Process your updates here
            String chatId = update.message().chat().id().toString();
            if (update.message().text() != null) {
                String messageText = update.message().text();
                if (("/start").equals(messageText)) {
                    telegramBot.execute(new SendMessage(
                            chatId, "Hello! To create a task, write a message in the form: 01.01.2022 20:00 Buy cat food")
                    );
                }

                boolean check = messageText.matches("([0-9\\.]{10})(\\s)([0-9\\:]{5})(\\s)([А-яA-z\\s\\d\\D]+)");

                if (!check & !("/start").equals(messageText)) {
                    telegramBot.execute(new SendMessage(
                            chatId, "To create a task, write a message in the form: 01.01.2022 20:00 Buy cat food")
                    );
                }

                Pattern pattern = Pattern.compile("([0-9\\.]{10})(\\s)([0-9\\:]{5})(\\s)([А-яA-z\\s\\d\\D]+)");
                Matcher matcher = pattern.matcher(messageText);

                while (matcher.find()) {
                    String date = matcher.group(1);
                    String time = matcher.group(3);
                    String dateTime = date + " " + time;
                    String item = matcher.group(5);
                    LocalDateTime localDateTime = LocalDateTime.parse(
                            dateTime, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
                    );


                    if (LocalDateTime.now().isAfter(localDateTime)) {
                        telegramBot.execute(new SendMessage(chatId, "Invalid date"));
                    } else {
                        notification_taskMapper.toDto(
                                notification_taskRepository.save(
                                        notification_taskMapper.toEntity(
                                                new Notification_taskDtoIn(Long.parseLong(chatId), item, localDateTime)))
                        );
                        telegramBot.execute(new SendMessage(
                                chatId, "Task successfully added")
                        );
                    }
                }

                run();
            }
        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void run() {
        LocalDateTime nowDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        notification_taskRepository.findNotificationTasksWithNowDataTime(nowDateTime).stream()
                .forEach(n -> telegramBot.execute(new SendMessage(n.getChatId(), n.getNotification())));
    }

}
