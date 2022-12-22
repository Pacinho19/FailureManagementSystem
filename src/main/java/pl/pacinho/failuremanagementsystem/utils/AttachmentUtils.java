package pl.pacinho.failuremanagementsystem.utils;

import java.io.File;
import java.util.Date;

import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;


public class AttachmentUtils {
    @SneakyThrows
    public static String getName(String path, Long taskNumber) {
        File file = FileUtils.getFile(path);
        return taskNumber + "_" + DateUtils.getDateTimeAsFileFormat(new Date()) + "_" + FilenameUtils.getBaseName(file.getName());
    }

    public static String getBaseName(String path) {
        return FileUtils.getFile(path)
                .getName();
    }
}
