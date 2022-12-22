package pl.pacinho.failuremanagementsystem.ui.model.mapper;

import pl.pacinho.failuremanagementsystem.model.entity.TaskMessage;
import pl.pacinho.failuremanagementsystem.ui.model.MessageDto;

import java.util.List;

public class MessageDtoMapper {
    public static List<MessageDto> parseList(List<TaskMessage> messages) {
        return messages.stream()
                .map(MessageDtoMapper::toDto)
                .toList();
    }

    private static MessageDto toDto(TaskMessage taskMessage) {
        return new MessageDto(taskMessage.getUser().getFirstName() + " " + taskMessage.getUser().getLastName(),
                taskMessage.getCreationDate(),
                taskMessage.getText()
        );
    }
}
