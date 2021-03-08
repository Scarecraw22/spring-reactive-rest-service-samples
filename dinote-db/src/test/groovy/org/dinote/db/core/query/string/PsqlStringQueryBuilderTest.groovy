package org.dinote.db.core.query.string


import spock.lang.Specification
import spock.lang.Unroll

class PsqlStringQueryBuilderTest extends Specification {

    def factory = new PsqlStringQueryBuilderFactory()

    def "SELECT FROM query should be built with success"() {
        given:
        StringQueryBuilder queryBuilder = factory.create()

        when:
        String query = queryBuilder.selectAll()
                .from("dinote", "user")
                .build()

        then:
        query == "SELECT * FROM dinote.user"
    }

    def "SELECT query with no columns provided should throw exception"() {
        given:
        StringQueryBuilder queryBuilder = factory.create()

        when:
        queryBuilder.select(List.of())

        then:
        thrown(IllegalArgumentException.class)
    }

    @Unroll
    def "SELECT FROM table with name #tableName should throw exception"() {
        given:
        StringQueryBuilder queryBuilder = factory.create()

        when:
        queryBuilder.select("col").from(tableName)

        then:
        thrown(IllegalArgumentException.class)

        where:
        tableName | _
        ""        | _
        "   "     | _
        null      | _
    }

    def "WHERE statement with null provided should throw exception"() {
        given:
        StringQueryBuilder queryBuilder = factory.create()

        when:
        queryBuilder.select("col").from("table").where(null)

        then:
        thrown(NullPointerException.class)
    }

    def "SELECT * FROM _ WHERE query should be built with success"() {
        given:
        StringQueryBuilder queryBuilder = factory.create()

        when:
        String query = queryBuilder.select("id", "name")
                .from("user")
                .where("id = :id")
                .build()

        then:
        query == "SELECT id, name FROM user WHERE id = :id"
    }

    def "SELECT * FROM _ WHERE _ AND query should be built with success"() {
        given:
        StringQueryBuilder queryBuilder = factory.create()

        when:
        String query = queryBuilder.select("id", "name", "created_on")
                .from("user")
                .where("id = :id")
                .and("name like :name")
                .build()

        then:
        query == "SELECT id, name, created_on FROM user WHERE id = :id AND name like :name"
    }

    def "SELECT * FROM _ WHERE _ OR query should be built with success"() {
        given:
        StringQueryBuilder queryBuilder = factory.create()

        when:
        String query = queryBuilder.select("id", "name", "created_on")
                .from("user")
                .where("id = :id")
                .or("name like :name")
                .build()

        then:
        query == "SELECT id, name, created_on FROM user WHERE id = :id OR name like :name"
    }

    def "INSERT INTO _ VALUES _ query built with success"() {
        given:
        StringQueryBuilder queryBuilder = factory.create()

        when:
        String query = queryBuilder.insertInto("user", List.of("id", "name", "password"))
                .values(":id", ":name", ":password")
                .build()

        then:
        query == "INSERT INTO user(id, name, password) VALUES(:id, :name, :password)"
    }

    def "INSERT INTO _ VALUES _ RETURNING * query built with success"() {
        given:
        StringQueryBuilder queryBuilder = factory.create()

        when:
        String query = queryBuilder.insertInto("user", List.of("id", "name", "password"))
                .values(":id", ":name", ":password")
                .returningAll()
                .build()

        then:
        query == "INSERT INTO user(id, name, password) VALUES(:id, :name, :password) RETURNING *"
    }

    def "DELETE FROM _ WHERE _ RETURNING * query built with success"() {
        given:
        StringQueryBuilder queryBuilder = factory.create()

        when:
        String query = queryBuilder.deleteFrom("user")
                .where("id = :id")
                .returningAll()
                .build();

        then:
        query == "DELETE FROM user WHERE id = :id RETURNING *"
    }
}
