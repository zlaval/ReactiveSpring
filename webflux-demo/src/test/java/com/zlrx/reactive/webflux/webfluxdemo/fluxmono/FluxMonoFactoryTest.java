package com.zlrx.reactive.webflux.webfluxdemo.fluxmono;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Optional;

public class FluxMonoFactoryTest {


    private List<String> numbers = List.of("One", "Two", "Three", "Four");

    @Test
    public void fluxFromIterable() {
        var numberFlux = Flux.fromIterable(numbers).log();
        StepVerifier.create(numberFlux)
                .expectNextCount(3)
                .expectNext("Four")
                .verifyComplete();
    }

    @Test
    public void fluxFromArray() {
        var numberArray = new String[]{"One", "Two", "Three", "Four"};
        var stringFlux = Flux.fromArray(numberArray).log();
        StepVerifier.create(stringFlux)
                .expectNext("One", "Two", "Three", "Four")
                .verifyComplete();
    }

    @Test
    public void fluxFromStream() {
        var numberStream = numbers.stream();
        var stringFlux = Flux.fromStream(numberStream).log();
        StepVerifier.create(stringFlux)
                .expectNext("One", "Two", "Three", "Four")
                .verifyComplete();
    }

    @Test
    public void fluxFromRange() {
        var range = Flux.range(1, 10).log();
        StepVerifier.create(range)
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    public void monoFactories() {
        StepVerifier.create(Mono.justOrEmpty(Optional.empty()).log())
                .verifyComplete();

        StepVerifier.create(
                Mono.fromSupplier(() -> "One").log())
                .expectNext("One")
                .verifyComplete();
    }

}
