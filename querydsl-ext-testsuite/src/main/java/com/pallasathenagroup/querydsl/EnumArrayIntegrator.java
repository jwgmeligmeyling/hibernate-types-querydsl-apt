package com.pallasathenagroup.querydsl;

import org.hibernate.boot.Metadata;
import org.hibernate.engine.jdbc.connections.spi.JdbcConnectionAccess;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class EnumArrayIntegrator implements Integrator {
    @Override
    public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactoryImplementor, SessionFactoryServiceRegistry sessionFactoryServiceRegistry) {
        JdbcConnectionAccess bootstrapJdbcConnectionAccess = sessionFactoryImplementor.getJdbcServices().getBootstrapJdbcConnectionAccess();
        try {
            Connection connection = bootstrapJdbcConnectionAccess.obtainConnection();

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(
                        "DROP TYPE IF EXISTS sensor_state CASCADE"
                );
            } finally {
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(
                            "CREATE TYPE sensor_state AS ENUM ('ONLINE', 'OFFLINE', 'UNKNOWN')"
                    );
                } finally {
                    bootstrapJdbcConnectionAccess.releaseConnection(connection);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void disintegrate(SessionFactoryImplementor sessionFactoryImplementor, SessionFactoryServiceRegistry sessionFactoryServiceRegistry) {

    }
}
