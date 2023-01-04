package pl.pacinho.failuremanagementsystem.utils;

import org.apache.commons.io.FilenameUtils;

import java.nio.file.Files;
import java.nio.file.Path;

public class ExistingFileRenameUtils {

    /**
     * test.png
     * test-2.png
     * test-3.png
     * test-4.png
     */
    public static String renameIfExists(Path uploadDir, String fileName) {
        return renameAndCheck(uploadDir, fileName);
    }

    private static String renameAndCheck(Path uploadDir, String fileName) {
        if (Files.exists(uploadDir.resolve(fileName))) {
            return renameAndCheck(uploadDir, renameFileName(fileName));
        }
        return fileName;
    }

    private static String renameFileName(String fileName) {
        String baseName = FilenameUtils.getBaseName(fileName);
        String[] split = baseName.split("-(?=\\d+$)");
        int counter = split.length>1 ? Integer.parseInt(split[1])+1 : 1;
        return split[0]+"-"+counter+"." + FilenameUtils.getExtension(fileName);
    }
}
