package com.zlrx.springreactive.springreactive.controller.v1;

import com.zlrx.springreactive.springreactive.document.ItemCapped;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@DirtiesContext
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
public class ItemStreamControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void streamAllItemsTest() throws InterruptedException {
        Thread.sleep(6000);
        Flux<ItemCapped> itemCappedFlux = webTestClient.get()
                .uri("/api/v1/item-stream")
                .exchange()
                .expectStatus().isOk()
                .returnResult(ItemCapped.class)
                .getResponseBody()
                .take(5);
        StepVerifier.create(itemCappedFlux)
                .expectNextCount(5)
                .thenCancel()
                .verify();
    }


}
