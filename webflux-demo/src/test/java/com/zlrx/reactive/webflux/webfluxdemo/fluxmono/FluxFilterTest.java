package com.zlrx.reactive.webflux.webfluxdemo.fluxmono;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

public class FluxFilterTest {

    private List<String> numbers = List.of("One", "Two", "Three", "Four");

    @Test
    public void filterTest() {
        var numberFlux = Flux.fromIterable(numbers)
                .filter(s -> s.startsWith("T"))
                .log();

        StepVerifier.create(numberFlux)
                .expectNext("Two", "Three")
                .verifyComplete();
    }


    @Test
    public void filterLengthTest() {
        var numberFlux = Flux.fromIterable(numbers)
                .filter(s -> s.length() > 3)
                .log();

        StepVerifier.create(numberFlux)
                .expectNext("Three", "Four")
                .verifyComplete();
    }
}
