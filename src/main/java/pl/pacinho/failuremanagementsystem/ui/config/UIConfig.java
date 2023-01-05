package pl.pacinho.failuremanagementsystem.ui.config;

public class UIConfig {
    public static final String PREFIX = "/falsi";
    public static final String LOGIN = PREFIX + "/login";
    public static final String HOME = PREFIX + "/home";
    public static final String TASK = PREFIX + "/task";
    public static final String NOTIFICATION = PREFIX + "/notification";
    public static final String ATTACHMENT = PREFIX + "/attachment";
    public static final String ATTACHMENT_DOWNLOAD = ATTACHMENT + "/download/{id}";
    public static final String NEW_TASK = TASK + "/new";
    public static final String TASK_PAGE = TASK + "/{number}";
    public static final String TASK_SEND_MESSAGE = TASK_PAGE + "/message";
    public static final String TASK_ACTION = TASK_PAGE + "/action";
    public static final String TASK_BIND_TASK = TASK_PAGE + "/bind";
    public static final String TASK_ADD_ATTACHMENT = TASK_PAGE + "/add-attachment";
    public static final String NOTIFICATION_ID = NOTIFICATION + "/{id}";
    public static final String NOTIFICATION_READ = NOTIFICATION_ID + "/read";
    public static final String NOTIFICATION_READ_ALL = NOTIFICATION + "/read-all";
}