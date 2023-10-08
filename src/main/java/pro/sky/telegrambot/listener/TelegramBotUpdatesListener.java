package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

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
            if (updates.equals("/start")) {
                telegramBot.execute(new SendMessage(chatId, "Hello! To create a task, write a message in the form 01.01.2022 20:00 Buy cat food"));
            } else {
                telegramBot.execute(new SendMessage(chatId, "Hi, i am still stupid!"));
            }

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}
