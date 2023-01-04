package pl.pacinho.failuremanagementsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import pl.pacinho.failuremanagementsystem.model.enums.AttachmentSource;
import pl.pacinho.failuremanagementsystem.utils.AttachmentUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Attachment {

    @Id
    @GenericGenerator(name = "attachmentIdGen", strategy = "increment")
    @GeneratedValue(generator = "attachmentIdGen")
    private Long id;

    private String name;
    private String baseName;
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TASK_ID")
    @JsonIgnore
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @JsonIgnore
    private User user;

    private LocalDateTime uploadDate;

    @Enumerated(EnumType.STRING)
    private AttachmentSource source;

    public Attachment(String path, String baseName, Task task, User user, AttachmentSource source) {
        this.path = path;
        this.name = AttachmentUtils.getName(path);
        this.baseName = baseName;
        this.task = task;
        this.user = user;
        this.source = source;
        this.uploadDate = LocalDateTime.now();
    }
}
