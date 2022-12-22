package pl.pacinho.failuremanagementsystem.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private final static SimpleDateFormat FILE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

    public static String getDateTimeAsFileFormat(Date date) {
        return FILE_FORMAT.format(date);
    }
}
