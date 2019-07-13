package com.zlrx.reactive.webflux.webfluxdemo.controller;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@WebFluxTest(value = ReactiveController.class)
@RunWith(SpringRunner.class)
@DirtiesContext
public class ReactiveControllerTest {

    @Autowired
    public WebTestClient webTestClient;

    @Test
    public void testNumberFlux() {
        var numberStream = webTestClient.get().uri("/api/v1/numbers")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody();

        StepVerifier.create(numberStream.log())
                .expectSubscription()
                .expectNext(0)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .verifyComplete();
    }

    @Test
    public void testNumberFlux2() {
        webTestClient.get().uri("/api/v1/numbers")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_STREAM_JSON_VALUE)
                .expectBodyList(Integer.class)
                .hasSize(4);
    }

    @Test
    public void testNumberFlux3() {
        var result = webTestClient.get().uri("/api/v1/numbers")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .returnResult()
                .getResponseBody();
        assertThat(result, CoreMatchers.hasItems(0, 1, 2, 3));
    }

    @Test
    public void testNumberFlux4() {
        webTestClient.get().uri("/api/v1/numbers")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .consumeWith(resp -> assertEquals(List.of(0, 1, 2, 3), resp.getResponseBody()));
    }

    @Test
    public void testInfiniteNumberFlux() {
        var numberStream = webTestClient.get().uri("/api/v1/infinite-numbers")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Long.class)
                .getResponseBody();

        StepVerifier.create(numberStream.log())
                .expectSubscription()
                .expectNext(0L)
                .expectNext(1L)
                .expectNext(2L)
                .thenCancel()
                .verify();
    }

    @Test
    public void testMonoNumber() {
        webTestClient.get().uri("/api/v1/mono-number")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Integer.class)
                .consumeWith(res -> assertEquals(0, res.getResponseBody().intValue()));
    }

}
