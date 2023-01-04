package pl.pacinho.failuremanagementsystem.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;

import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;


public class AttachmentUtils {

    public static final String BASE_PATH = "attachments/";

    @SneakyThrows
    public static String getName(String path) {
        File file = FileUtils.getFile(BASE_PATH + path);
        return file.getName();
    }

    @SneakyThrows
    public static String save(long number, MultipartFile multipartFile) {
        if( multipartFile.isEmpty())
            throw new IllegalArgumentException("File not found!");

        String taskDir = BASE_PATH + "/" + number;
        createDir(taskDir);

        String filePath = number + "/" +
                          ExistingFileRenameUtils.renameIfExists(
                                  Path.of(taskDir),
                                  getNewName(multipartFile.getOriginalFilename())
                          );
        File file = new File(BASE_PATH + filePath);
        try (OutputStream os = new FileOutputStream(file)) {
            os.write(multipartFile.getBytes());
        }
        return filePath;
    }

    private static void createDir(String path) {
        new File(path)
                .mkdirs();
    }

    private static String getNewName(String name) {
        return SlugifyUtils.slugify(name);
    }
}
