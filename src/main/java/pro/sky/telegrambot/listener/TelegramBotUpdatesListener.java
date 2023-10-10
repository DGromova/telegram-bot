package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import liquibase.pro.packaged.S;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            String chatId = update.message().chat().id().toString();
            String messageText = update.message().text();
            // Process your updates here
            if (messageText.equals("/start")) {
                telegramBot.execute(new SendMessage(chatId, "Hello! To create a task, write a message in the form: 01.01.2022 20:00 Buy cat food"));
            }

            Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([А-яA-z\\s.\\d\\D]+)");
            Matcher matcher = pattern.matcher(messageText);
            while (matcher.find()) {
                String dateTime = matcher.group(1);
                String item = matcher.group(3);
//                System.out.println("group 1: " + matcher.group(1));
//                System.out.println("group 3: " + matcher.group(3));
            }

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
