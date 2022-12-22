package pl.pacinho.failuremanagementsystem.utils;

import lombok.SneakyThrows;

import java.io.File;
import java.io.FileNotFoundException;

public class FileUtils {

    @SneakyThrows
    public static File getFile(String path) {
        File file = new File(path);
        if (!file.exists()) throw new FileNotFoundException("File not found: " + file.getName());
        return file;
    }
}
