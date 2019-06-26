package com.zlrx.springreactive.springreactive.fluxmono;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxMonoTest {

    @Test
    public void fluxTest() {
        var stringFlux = Flux.just("One", "Two", "Three")
                .concatWith(Flux.error(new RuntimeException("Test exception")))
                .concatWith(Flux.just("After error"))
                .log();

        stringFlux.subscribe(
                System.out::println,
                (e) -> System.out.println(e),
                () -> System.out.println("Completed"));
    }

    @Test
    public void fluxTestElementsWithoutError() {
        var stringFlux = Flux.just("One", "Two", "Three").log();

        StepVerifier.create(stringFlux)
                .expectNext("One")
                .expectNext("Two")
                .expectNext("Three")
                .verifyComplete();

    }

    @Test
    public void fluxTestElementsWithError() {
        var stringFlux = Flux.just("One", "Two", "Three")
                .concatWith(Flux.error(new RuntimeException("Test exception")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("One")
                .expectNext("Two")
                .expectNext("Three")
                .verifyError(RuntimeException.class);

    }
}
