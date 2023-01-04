package pl.pacinho.failuremanagementsystem.ui.model.mapper;

import pl.pacinho.failuremanagementsystem.model.dto.mapper.UserDtoMapper;
import pl.pacinho.failuremanagementsystem.model.entity.Attachment;
import pl.pacinho.failuremanagementsystem.ui.model.AttachmentDto;

import java.util.List;

public class AttachmentDtoMapper {
    public static List<AttachmentDto> parseList(List<Attachment> attachments) {
        return attachments.stream()
                .map(AttachmentDtoMapper::toDto)
                .toList();
    }

    private static AttachmentDto toDto(Attachment attachment) {
        return new AttachmentDto(
                attachment.getId(),
                attachment.getBaseName(),
                attachment.getUploadDate(),
                UserDtoMapper.parseToDto(attachment.getUser()),
                attachment.getSource()
        );
    }
}
