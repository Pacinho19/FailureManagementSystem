package pl.pacinho.failuremanagementsystem.utils;

import pl.pacinho.failuremanagementsystem.model.entity.Task;
import pl.pacinho.failuremanagementsystem.model.entity.TaskMessage;
import pl.pacinho.failuremanagementsystem.model.enums.MessageType;

public class MessageUtils {

    public static String getColor(TaskMessage message, Task task) {
        if (message.getType() == MessageType.SYS)
            return ColorUtils.MESSAGE_SYS;

        if(task.getOwner().getDepartment()==message.getUser().getDepartment())
            return ColorUtils.MESSAGE_OWNER;

        if(task.getExecutor()!=null && task.getExecutor().getDepartment()==message.getUser().getDepartment())
            return ColorUtils.MESSAGE_EXECUTOR;

        return ColorUtils.MESSAGE_OTHER;
    }

}
