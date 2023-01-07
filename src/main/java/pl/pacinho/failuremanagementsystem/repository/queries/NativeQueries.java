package pl.pacinho.failuremanagementsystem.repository.queries;

public class NativeQueries {
    public static final String TASK_SEARCH = """
            SELECT DISTINCT
                t.number as number, t.title as title, 'NUMBER' as type
            FROM
                TASK t
            WHERE
                ('' || t.number) containing :searchText
            
            UNION
            
            SELECT DISTINCT
                t.number as number, t.title as title, 'TITLE' as type
            FROM
                TASK t
            WHERE
                lower(t.title) containing lower(:searchText)
                
            UNION
            
            SELECT DISTINCT
                t.number as number, t.title as title, 'PURPOSE' as type
            FROM
                TASK t
            WHERE
                lower(t.purpose) containing lower(:searchText)
                
            UNION
            
            SELECT DISTINCT
                t.number as number, t.title as title, 'MESSAGE' as type
            FROM
                TASK t
            JOIN
                TASK_MESSAGE m on m.task_id=t.number
            WHERE
                m.type is distinct from 'SYS'
                and lower(m.text) containing lower(:searchText)

            UNION
            
            SELECT DISTINCT
                t.number as number, t.title as title, 'ATTACHMENT_NAME' as type
            FROM
                TASK t
            JOIN
                Attachment a on a.task_id=t.number
            WHERE
                lower(a.base_name) containing lower(:searchText)
            """;
}