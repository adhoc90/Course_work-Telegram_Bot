package pro.sky.telegrambot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;
import pro.sky.telegrambot.service.NotificationTaskService;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class NotificationTaskServiceImpl implements NotificationTaskService {

    private final NotificationTaskRepository notificationTaskRepository;

    public void save(NotificationTask task) {
        notificationTaskRepository.save(task);
    }

    public void delete(NotificationTask task) {
        notificationTaskRepository.delete(task);
    }

    public Collection<NotificationTask> findAllByDateTimeEquals(LocalDateTime localDateTime) {
        return notificationTaskRepository.findAllByDateTimeEquals(localDateTime);
    }
}