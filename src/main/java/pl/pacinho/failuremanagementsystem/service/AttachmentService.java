package pl.pacinho.failuremanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pacinho.failuremanagementsystem.exception.AttachmentNotFoundException;
import pl.pacinho.failuremanagementsystem.model.entity.Attachment;
import pl.pacinho.failuremanagementsystem.model.entity.User;
import pl.pacinho.failuremanagementsystem.repository.AttachmentRepository;
import pl.pacinho.failuremanagementsystem.utils.AttachmentUtils;
import pl.pacinho.failuremanagementsystem.utils.FileUtils;

import java.io.File;

@RequiredArgsConstructor
@Service
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;

    public File getAttachment(long id) {
        Attachment attachment = attachmentRepository.findById(id)
                .orElseThrow(AttachmentNotFoundException::new);

        return FileUtils.getFile(AttachmentUtils.BASE_PATH + attachment.getPath());
    }
}