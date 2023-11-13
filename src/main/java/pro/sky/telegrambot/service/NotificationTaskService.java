package pro.sky.telegrambot.service;

import pro.sky.telegrambot.model.NotificationTask;

import java.time.LocalDateTime;
import java.util.Collection;

public interface NotificationTaskService {

    void save(NotificationTask task);

    void delete(NotificationTask task);

    Collection<NotificationTask> findAllByDateTimeEquals(LocalDateTime localDateTime);
}