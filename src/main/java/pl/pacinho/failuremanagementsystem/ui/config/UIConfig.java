package pl.pacinho.failuremanagementsystem.ui.config;

public class UIConfig {
    public static final String PREFIX = "/falsi";
    public static final String LOGIN = PREFIX + "/login";
    public static final String HOME = PREFIX + "/home";
    public static final String TASK = PREFIX + "/task";

    public static final String NEW_TASK = TASK + "/new";
    public static final String MY_TASKS = TASK + "/my";
    public static final String TASK_PAGE = TASK + "/{number}";
    public static final String TASK_SEND_MESSAGE = TASK_PAGE + "/message";
}
