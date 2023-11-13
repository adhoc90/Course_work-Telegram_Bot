package pro.sky.telegrambot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.configuration.TelegramBotConfiguration;
import pro.sky.telegrambot.exception.IncorrectCreateTaskCommandException;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.service.CommandHandlerService;
import pro.sky.telegrambot.service.NotificationTaskService;
import pro.sky.telegrambot.util.MessageUtil;

@Service
@RequiredArgsConstructor
public class CommandHandlerServiceImpl implements CommandHandlerService {

    private final NotificationTaskService notificationTaskService;
    private final TelegramBotConfiguration tgBotConfig;

    private static final String START_COMMAND = "/start";
    private static final String HELP_COMMAND = "/help";

    @Override
    public String commandHandler(Long chat_id, String command) {
        switch (command) {
            case START_COMMAND:
                return tgBotConfig.getStartMsg();
            case HELP_COMMAND:
                return tgBotConfig.getHelpMsg();
            default:
                return handleCreateTaskCommand(chat_id, command);
        }
    }

    private String handleCreateTaskCommand(Long chat_id, String command) {
        try {
            NotificationTask notificationTask = MessageUtil.pasreCreateCommand(chat_id, command);
            notificationTaskService.save(notificationTask);
            return tgBotConfig.getSuccessMsg();
        } catch (IncorrectCreateTaskCommandException e) {
            return tgBotConfig.getErrorMsg();
        }
    }
}