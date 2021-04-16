package org.dinote.db.core.query.string;

public class PsqlStringQueryBuilderFactory implements StringQueryBuilderFactory {

    @Override
    public StringQueryBuilder create() {
        return new PsqlStringQueryBuilder();
    }
}
