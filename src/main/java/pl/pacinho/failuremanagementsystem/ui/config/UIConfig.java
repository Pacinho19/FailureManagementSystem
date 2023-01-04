package pl.pacinho.failuremanagementsystem.ui.config;

public class UIConfig {
    public static final String PREFIX = "/falsi";
    public static final String LOGIN = PREFIX + "/login";
    public static final String HOME = PREFIX + "/home";
    public static final String TASK = PREFIX + "/task";
    public static final String NEW_TASK = TASK + "/new";
    public static final String TASK_PAGE = TASK + "/{number}";
    public static final String TASK_SEND_MESSAGE = TASK_PAGE + "/message";
    public static final String TASK_ACTION = TASK_PAGE + "/action";
    public static final String TASK_BIND_TASK = TASK_PAGE + "/bind";
    public static final String TASK_ADD_ATTACHMENT = TASK_PAGE + "/add-attachment";
}
