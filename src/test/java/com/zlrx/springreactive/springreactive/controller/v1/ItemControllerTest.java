package com.zlrx.springreactive.springreactive.controller.v1;


import com.zlrx.springreactive.springreactive.document.Item;
import com.zlrx.springreactive.springreactive.repository.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertTrue;

@DirtiesContext
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
public class ItemControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ItemRepository itemRepository;

    private List<Item> items = List.of(
            new Item(null, "Mobile", 200L),
            new Item(null, "Notebook", 300L),
            new Item("mockid", "TV", 400L)
    );

    @Before
    public void setUp() {
        itemRepository.deleteAll()
                .thenMany(Flux.fromIterable(items))
                .flatMap(itemRepository::save)
                .blockLast();
    }

    @Test
    public void getAllItems() {
        webTestClient.get()
                .uri("/api/v1/item")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Item.class)
                .hasSize(3);
    }

    @Test
    public void getAllItems2() {
        webTestClient.get()
                .uri("/api/v1/item")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Item.class)
                .hasSize(3)
                .consumeWith(response -> {
                    List<Item> items = response.getResponseBody();
                    items.forEach(item -> assertTrue(Objects.nonNull(item.getId())));
                });
    }

    @Test
    public void getAllItems3() {
        Flux<Item> itemFlux = webTestClient.get()
                .uri("/api/v1/item")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .returnResult(Item.class)
                .getResponseBody();
        StepVerifier.create(itemFlux.log())
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    public void getItem() {
        webTestClient.get()
                .uri("/api/v1/item/{id}", "mockid")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.price", 400);
    }

    @Test
    public void getItemNotFound() {
        webTestClient.get()
                .uri("/api/v1/item/{id}", "noid")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void saveItem() {
        var item = new Item(null, "Car", 2111L);
        webTestClient.post()
                .uri("/api/v1/item")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(item), Item.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.price", 2111L);
    }

    @Test
    public void deleteItem() {
        webTestClient.delete()
                .uri("/api/v1/item/{id}", "mockid")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Void.class);
    }

    @Test
    public void updateItem() {
        var item = new Item(null, "TV", 500L);
        webTestClient.put()
                .uri("/api/v1/item/{id}", "mockid")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(item), Item.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.price", 500);
    }

    @Test
    public void updateItemInvalidId() {
        var item = new Item(null, "TV", 500L);
        webTestClient.put()
                .uri("/api/v1/item/{id}", "noid")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(item), Item.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void exceptionHandlerTest() {
        webTestClient.get()
                .uri("/api/v1/item/runtime-exception")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(String.class)
                .isEqualTo("Something went wrong");
    }

}
