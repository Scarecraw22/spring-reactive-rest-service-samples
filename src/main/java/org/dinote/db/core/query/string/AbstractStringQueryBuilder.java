package org.dinote.db.core.query.string;

import org.dinote.utils.DinoteStringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Contains default implementation of {@link StringQueryBuilder} where
 * most of SQL engines should accept.
 *
 * @author Oskar Wąsikowski
 */
abstract class AbstractStringQueryBuilder implements StringQueryBuilder {

    protected static final String SELECT = " SELECT ";
    protected static final String SELECT_ALL = " SELECT * ";
    protected static final String FROM = " FROM ";
    protected static final String WHERE = " WHERE ";
    protected static final String AND = " AND ";
    protected static final String OR = " OR ";
    protected static final String INSERT_INTO = " INSERT INTO ";
    protected static final String VALUES = " VALUES";
    protected static final String DELETE_FROM = " DELETE FROM ";

    protected final StringBuilder query;

    AbstractStringQueryBuilder() {
        this.query = new StringBuilder();
    }

    @Override
    public StringQueryBuilder select(String col1) {
        return select(List.of(col1));
    }

    @Override
    public StringQueryBuilder select(String col1, String col2) {
        return select(List.of(col1, col2));
    }

    @Override
    public StringQueryBuilder select(String col1, String col2, String col3) {
        return select(List.of(col1, col2, col3));
    }

    @Override
    public StringQueryBuilder select(String col1, String col2, String col3, String col4) {
        return select(List.of(col1, col2, col3, col4));
    }

    @Override
    public StringQueryBuilder select(String col1, String col2, String col3, String col4, String col5) {
        return select(List.of(col1, col2, col3, col4, col5));
    }

    @Override
    public StringQueryBuilder select(String col1, String col2, String col3, String col4, String col5, String col6) {
        return select(List.of(col1, col2, col3, col4, col5, col6));
    }

    @Override
    public StringQueryBuilder select(String col1, String col2, String col3, String col4, String col5, String col6, String col7) {
        return select(List.of(col1, col2, col3, col4, col5, col7));
    }

    @Override
    public StringQueryBuilder select(List<String> cols) {
        if (cols == null || cols.isEmpty()) {
            throw new IllegalArgumentException("There is no columns provided. If you want to use '*' wildcard use selectAll()");
        }
        cols.forEach(Objects::requireNonNull);

        query.append(SELECT)
                .append(cols.stream().collect(Collectors.joining(", ", "", "")));

        return this;
    }

    @Override
    public StringQueryBuilder selectAll() {
        query.append(SELECT_ALL);

        return this;
    }

    @Override
    public StringQueryBuilder from(String table) {
        return from(null, table);
    }

    @Override
    public StringQueryBuilder from(String schema, String table) {
        if (DinoteStringUtils.isNullOrBlank(table)) {
            throw new IllegalArgumentException("Table is null or blank");
        }
        String fullTableName = DinoteStringUtils.isNullOrBlank(schema) ? table : schema + "." + table;

        query.append(FROM)
                .append(fullTableName);

        return this;
    }

    @Override
    public StringQueryBuilder where(String rawCondition) {
        Objects.requireNonNull(rawCondition);
        query.append(WHERE)
                .append(rawCondition);

        return this;
    }

    @Override
    public StringQueryBuilder and(String rawCondition) {
        Objects.requireNonNull(rawCondition);
        query.append(AND)
                .append(rawCondition);
        return this;
    }

    @Override
    public StringQueryBuilder or(String rawCondition) {
        Objects.requireNonNull(rawCondition);
        query.append(OR)
                .append(rawCondition);
        return this;
    }

    @Override
    public StringQueryBuilder insertInto(String table, List<String> cols) {
        return insertInto(null, table, cols);
    }

    @Override
    public StringQueryBuilder insertInto(String schema, String table, List<String> cols) {
        Objects.requireNonNull(cols);
        if (DinoteStringUtils.isNullOrBlank(table)) {
            throw new IllegalArgumentException("Table cannot be null or blank");
        }

        String fullTableName = DinoteStringUtils.isNullOrBlank(schema) ? table : schema + "." + table;
        query.append(INSERT_INTO)
                .append(fullTableName);
        if (cols.isEmpty()) {
            return this;
        }

        query.append("(")
                .append(joinWithComa(cols))
                .append(")");
        return this;
    }

    @Override
    public StringQueryBuilder values(String col1) {
        return values(List.of(col1));
    }

    @Override
    public StringQueryBuilder values(String col1, String col2) {
        return values(List.of(col1, col2));
    }

    @Override
    public StringQueryBuilder values(String col1, String col2, String col3) {
        return values(List.of(col1, col2, col3));
    }

    @Override
    public StringQueryBuilder values(String col1, String col2, String col3, String col4) {
        return values(List.of(col1, col2, col3, col4));
    }

    @Override
    public StringQueryBuilder values(String col1, String col2, String col3, String col4, String col5) {
        return values(List.of(col1, col2, col3, col4, col5));
    }

    @Override
    public StringQueryBuilder values(String col1, String col2, String col3, String col4, String col5, String col6) {
        return values(List.of(col1, col2, col3, col4, col5, col6));
    }

    @Override
    public StringQueryBuilder values(String col1, String col2, String col3, String col4, String col5, String col6, String col7) {
        return values(List.of(col1, col2, col3, col4, col5, col6, col7));
    }

    @Override
    public StringQueryBuilder values(List<String> cols) {
        if (cols == null || cols.isEmpty()) {
            throw new IllegalArgumentException("There are no columns provided");
        }

        query.append(VALUES)
                .append("(")
                .append(joinWithComa(cols))
                .append(")");
        return this;
    }

    @Override
    public StringQueryBuilder deleteFrom(String table) {
        return deleteFrom(null, table);
    }

    @Override
    public StringQueryBuilder deleteFrom(String schema, String table) {
        if (DinoteStringUtils.isNullOrBlank(table)) {
            throw new IllegalArgumentException("Table cannot be null or blank");
        }

        String fullTableName = DinoteStringUtils.isNullOrBlank(schema) ? table : schema + "." + table;
        query.append(DELETE_FROM)
                .append(fullTableName);

        return this;
    }

    @Override
    public String build() {
        return query.toString()
                .trim()
                .replaceAll("\\s{2,}", " ");
    }

    private String joinWithComa(List<String> list) {
        return String.join(", ", list);
    }
}
