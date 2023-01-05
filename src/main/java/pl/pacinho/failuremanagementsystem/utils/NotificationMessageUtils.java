package pl.pacinho.failuremanagementsystem.utils;

import java.util.function.Function;

public class NotificationMessageUtils {
    public static final Function<Long, String> NEW_MESSAGE = number -> getPrefix(number) + "New message";
    public static final Function<Long, String> CHANGE_TASK_STATUS = number -> getPrefix(number) + "Status changed";;
    public static final Function<Long, String> NEW_ATTACHMENT = number -> getPrefix(number) + "New attachment";
    public static final Function<Long, String> NEW_RELATED_TASK = number -> getPrefix(number) + "New related task";

    private static String getPrefix(long number){
        return  "#" + number + " | ";
    }
}