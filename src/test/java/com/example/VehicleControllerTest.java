package com.example;

import com.fasterxml.jackson.databind.JsonNode;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import jakarta.inject.Inject;

@MicronautTest
class VehicleControllerTest {

    @Inject
    EmbeddedServer server;

    @Inject
    @Client("/")
    HttpClient httpClient;

    @Test
    void testUnwrappedCar() {
        JsonNode val = httpClient.toBlocking().retrieve("/unwrapped/car", JsonNode.class);
        Assertions.assertEquals("car", val.path("type").asText());
    }

    @Test
    void testUnwrappedVan() {
        JsonNode val = httpClient.toBlocking().retrieve("/unwrapped/van", JsonNode.class);
        Assertions.assertEquals("van", val.path("type").asText());
    }

    @Test
    void testWrappedCar() {
        JsonNode val = httpClient.toBlocking().retrieve("/wrapped/car", JsonNode.class);
        Assertions.assertEquals("car", val.path("vehicle").path("type").asText());
    }

    @Test
    void testWrappedVan() {
        JsonNode val = httpClient.toBlocking().retrieve("/wrapped/van", JsonNode.class);
        Assertions.assertEquals("van", val.path("vehicle").path("type").asText());
    }


}
