package pl.pacinho.failuremanagementsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import pl.pacinho.failuremanagementsystem.utils.AttachmentUtils;

import javax.persistence.*;

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

    public Attachment(String path, Long taskNumber) {
        this.path = path;
        this.name = AttachmentUtils.getName(path, taskNumber);
        this.baseName = AttachmentUtils.getBaseName(path);
    }
}
