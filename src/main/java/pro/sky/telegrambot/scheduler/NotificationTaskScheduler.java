package pro.sky.telegrambot.scheduler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.service.NotificationTaskService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class NotificationTaskScheduler {

    private final Logger logger = LoggerFactory.getLogger(NotificationTaskScheduler.class);

    private final NotificationTaskService notificationTaskService;
    private final TelegramBot telegramBot;

    @Scheduled(cron = "0 0/1 * * * *")
    public void sendTaskNotification() {
        LocalDateTime dateTimeToFindTask = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        logger.info("Task Scheduler started, {}", dateTimeToFindTask);
        notificationTaskService.findAllByDateTimeEquals(dateTimeToFindTask).forEach(this::sendNotification);
    }

    private void sendNotification(NotificationTask notificationTask) {
        SendResponse response =
                telegramBot.execute(new SendMessage(notificationTask.getChatId(), notificationTask.getMessage()));
        if (response.isOk()) {
            notificationTaskService.delete(notificationTask);
        }
    }
}