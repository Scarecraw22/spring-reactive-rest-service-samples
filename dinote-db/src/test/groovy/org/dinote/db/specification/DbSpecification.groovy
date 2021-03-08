package org.dinote.db.specification

import org.flywaydb.core.Flyway
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

abstract class DbSpecification extends Specification {

    private static boolean migrated = false

    @Autowired
    private Flyway flyway

    def setup() {
        if (!migrated) {
            migrated = true
            flyway.migrate()
        }
    }
}
