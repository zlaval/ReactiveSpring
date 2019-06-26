package com.zlrx.springreactive.springreactive.fluxmono;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

public class FluxTransformTest {

    private List<String> numbers = List.of("One", "Two", "Three", "Four");

    @Test
    public void transformMapToUpperCase() {
        var numberFlux = Flux.fromIterable(numbers)
                .map(String::toUpperCase)
                .log();

        StepVerifier.create(numberFlux)
                .expectNext("ONE", "TWO", "THREE", "FOUR")
                .verifyComplete();
    }

    @Test
    public void transformMapToLength() {
        var numberFlux = Flux.fromIterable(numbers)
                .map(String::length)
                .log();

        StepVerifier.create(numberFlux)
                .expectNext(3, 3, 5, 4)
                .verifyComplete();
    }


    @Test
    public void transformMapToLengthRepeat() {
        var numberFlux = Flux.fromIterable(numbers)
                .map(String::length)
                .repeat(1)
                .log();

        StepVerifier.create(numberFlux)
                .expectNext(3, 3, 5, 4, 3, 3, 5, 4)
                .verifyComplete();
    }

    @Test
    public void transformFilterAndMapToLength() {
        var numberFlux = Flux.fromIterable(numbers)
                .filter(s -> s.length() < 4)
                .map(String::toUpperCase)
                .log();

        StepVerifier.create(numberFlux)
                .expectNext("ONE", "TWO")
                .verifyComplete();
    }

}
