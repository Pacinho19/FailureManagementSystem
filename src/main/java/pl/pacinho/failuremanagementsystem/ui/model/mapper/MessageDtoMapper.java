package pl.pacinho.failuremanagementsystem.ui.model.mapper;

import pl.pacinho.failuremanagementsystem.model.entity.TaskMessage;
import pl.pacinho.failuremanagementsystem.ui.model.MessageDto;
import pl.pacinho.failuremanagementsystem.utils.MessageUtils;

import java.util.List;
import java.util.stream.Collectors;

public class MessageDtoMapper {
    public static List<MessageDto> parseList(List<TaskMessage> messages) {
        return messages.stream()
                .map(MessageDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    private static MessageDto toDto(TaskMessage taskMessage) {
        return new MessageDto(
                taskMessage.getId(),
                taskMessage.getUser().getFirstName() + " " + taskMessage.getUser().getLastName(),
                taskMessage.getCreationDate(),
                taskMessage.getText(),
                taskMessage.getType(),
                MessageUtils.getColor(taskMessage, taskMessage.getTask()),
                taskMessage.getParent()!=null ? toDto(taskMessage.getParent()) : null
        );
    }
}
