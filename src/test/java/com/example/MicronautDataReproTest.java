package com.example;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.test.support.TestPropertyProvider;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@MicronautTest
public class MicronautDataReproTest implements TestPropertyProvider {

    @Container
    private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14.0")
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("postgres");

    @Inject
    BookRepository bookRepository;

    @Test
    public void tryUpdate() {
        UUID author = UUID.randomUUID();
        UUID updatedBook = UUID.randomUUID();
        bookRepository.save(new Book(updatedBook, author, UUID.randomUUID().toString(), false));
        bookRepository.save(new Book(UUID.randomUUID(), author, UUID.randomUUID().toString(), true));

        List<Book> books = bookRepository.readAllByAuthor(author);
        assertEquals(1, books.size());
        assertEquals(books.get(0).id(), updatedBook);
    }

    @Override
    public @NotNull Map<String, String> getProperties() {
        return Map.ofEntries(
                Map.entry("JDBC_URL", postgres.getJdbcUrl()),
                Map.entry("JDBC_USERNAME", postgres.getUsername()),
                Map.entry("JDBC_PASSWORD", postgres.getPassword()),
                Map.entry("JDBC_DRIVER", postgres.getDriverClassName())
        );
    }

}