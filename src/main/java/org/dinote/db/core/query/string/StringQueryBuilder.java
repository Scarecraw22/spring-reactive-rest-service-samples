package org.dinote.db.core.query.string;

import java.util.List;

/**
 * Defines operations for creating {@link String} type query to execute.
 * The query string can contain params like <code>":param"</code> that other
 * query tool can bind to correct database type and value.
 *
 * @author Oskar Wąsikowski
 */
public interface StringQueryBuilder {

    StringQueryBuilder select(String col1);

    StringQueryBuilder select(String col1, String col2);

    StringQueryBuilder select(String col1, String col2, String col3);

    StringQueryBuilder select(String col1, String col2, String col3, String col4);

    StringQueryBuilder select(String col1, String col2, String col3, String col4, String col5);

    StringQueryBuilder select(String col1, String col2, String col3, String col4, String col5, String col6);

    StringQueryBuilder select(String col1, String col2, String col3, String col4, String col5, String col6, String col7);

    StringQueryBuilder select(List<String> cols);

    StringQueryBuilder selectAll();

    StringQueryBuilder from(String table);

    StringQueryBuilder from(String schema, String table);

    StringQueryBuilder where(String rawCondition);

    StringQueryBuilder and(String rawCondition);

    StringQueryBuilder or(String rawCondition);

    StringQueryBuilder insertInto(String table, List<String> cols);

    StringQueryBuilder insertInto(String schema, String table, List<String> cols);

    StringQueryBuilder values(String col1);

    StringQueryBuilder values(String col1, String col2);

    StringQueryBuilder values(String col1, String col2, String col3);

    StringQueryBuilder values(String col1, String col2, String col3, String col4);

    StringQueryBuilder values(String col1, String col2, String col3, String col4, String col5);

    StringQueryBuilder values(String col1, String col2, String col3, String col4, String col5, String col6);

    StringQueryBuilder values(String col1, String col2, String col3, String col4, String col5, String col6, String col7);

    StringQueryBuilder values(List<String> cols);

    StringQueryBuilder returningAll();

    StringQueryBuilder deleteFrom(String table);

    StringQueryBuilder deleteFrom(String schema, String table);

    String build();
}
