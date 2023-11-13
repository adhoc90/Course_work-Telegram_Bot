package pro.sky.telegrambot.service;

public interface CommandHandlerService {

    String commandHandler(Long chat_id, String command);
}