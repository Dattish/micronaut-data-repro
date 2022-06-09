package com.example;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Introspected
public record Book(
        @Id UUID id,
        @NotNull UUID author,
        @NotNull String name,
        boolean read
) {
}
