package pl.pacinho.failuremanagementsystem.repository.queries;

public class NativeQueries {
    public static final String TASK_SEARCH_BY_NUMBER = """
            SELECT DISTINCT
                t.number as "number", t.title as "title", trim('NUMBER') as "type"
            FROM
                TASK t
            WHERE
                ('' || t.number) containing :searchText
            """;
    public static final String TASK_SEARCH_BY_TITLE = """
            SELECT DISTINCT
                t.number as "number", t.title as "title",  trim('TITLE') as "type"
            FROM
                TASK t
            WHERE
                lower(t.title) containing lower(:searchText)
            """;
    public static final String TASK_SEARCH_BY_PURPOSE = """
            SELECT DISTINCT
                t.number as "number", t.title as "title",  trim('PURPOSE') as "type"
            FROM
                TASK t
            WHERE
                lower(t.purpose) containing lower(:searchText)
            """;
    public static final String TASK_SEARCH_BY_MESSAGE = """
            SELECT DISTINCT
                t.number as "number", t.title as "title",  trim('MESSAGE') as "type"
            FROM
                TASK t
            JOIN
                TASK_MESSAGE m on m.task_id=t.number
            WHERE
                m.type is distinct from 'SYS'
                and lower(m.text) containing lower(:searchText)
            """;
    public static final String TASK_SEARCH_BY_ATTACHMENT_NAME = """
            SELECT DISTINCT
                t.number as "number", t.title as "title", trim('ATTACHMENT_NAME') as "type"
            FROM
                TASK t
            JOIN
                Attachment a on a.task_id=t.number
            WHERE
                lower(a.base_name) containing lower(:searchText)
            """;
}