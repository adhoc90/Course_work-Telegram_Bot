package pro.sky.telegrambot.util;

import org.springframework.util.StringUtils;
import pro.sky.telegrambot.exception.IncorrectCreateTaskCommandException;
import pro.sky.telegrambot.model.NotificationTask;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.time.LocalDateTime.parse;

public class MessageUtil {

//    Паттерн, которая поможет распознать дату и текст напоминания имеет вид ([0-9\.\:\s]{16})(\s)([\W+]+)
// Для того, чтобы из строки вида 01.01.2022 20:00 сконструировать объект класса LocalDateTime необходимо использовать
// кастомный паттерн и вызывать метод вида LocalDateTime.parse("01.01.2022 20:00", ([0-9\.\:\s]{16})(\s)(.+)
// DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))

    private static final Pattern pattern =
            Pattern.compile("([0-9\\.\\s]{10})(\\s)([0-9\\:\\s]{5})(\\s)([A-zА-я\\s\\d.,!:;]+)");
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public static NotificationTask pasreCreateCommand(Long chat_id, String command)
            throws IncorrectCreateTaskCommandException {
        if (StringUtils.hasText(command)) {
            Matcher matcher = pattern.matcher(command);

            if (matcher.find()) {
                String dateTimeStr = matcher.group(1) + matcher.group(2) + matcher.group(3);
                LocalDateTime dateTime = parse(dateTimeStr, dateTimeFormatter);
                String taskText = matcher.group(5);

                checkDate(dateTime);
                checkTaskText(taskText);
                return new NotificationTask(chat_id, taskText, dateTime);
            }
        }
        throw new IncorrectCreateTaskCommandException("Incorrect command: " + command);
    }


    private static void checkDate(LocalDateTime dateTime) throws IncorrectCreateTaskCommandException {
        if (dateTime == null) {
            throw new IncorrectCreateTaskCommandException("Incorrect text dateTime: " + dateTime);
        } else if (dateTime.isBefore(LocalDateTime.now())) {
            throw new IncorrectCreateTaskCommandException("Incorrect text dateTime: " + dateTime);
        }
    }

    public static void checkTaskText(String text) throws IncorrectCreateTaskCommandException {
        if (!StringUtils.hasText(text)) {
            throw new IncorrectCreateTaskCommandException("Incorrect task text: " + text);
        }
    }
}