package com.reservation.reservationapp.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;


/**
 * Spring Boot tries to guess the location of your @Repository
 * definitions, based on the {@link EnableAutoConfiguration }
 * it finds. To get more control, use the {@link EnableJpaRepositories} annotation
 * (from Spring Data JPA)
 *
 * Calls on Spring Data repositories are by default surrounded by a transaction
 * even without {@link EnableTransactionManagement}
 * If Spring Data finds an existing transaction, the existing transaction
 * will be re-used, otherwise a new transaction is created.
 * {@link Transactional} annotations within your own code
 * however, are only evaluated when you have
 * {@link EnableTransactionManagement} activated (or configured transaction handling some other way).
 */
@EnableJpaRepositories
@EnableTransactionManagement
public class DatabaseConfig {
}
