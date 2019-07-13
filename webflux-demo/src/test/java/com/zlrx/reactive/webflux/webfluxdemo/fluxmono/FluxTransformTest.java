package com.zlrx.reactive.webflux.webfluxdemo.fluxmono;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static reactor.core.scheduler.Schedulers.parallel;

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

    @Test
    public void transformFlatmap() {
        var numberFlux = Flux.fromIterable(numbers)
                .flatMap(s -> Flux.fromIterable(converToList(s)))
                .log();
        StepVerifier.create(numberFlux)
                .expectNextCount(8)
                .verifyComplete();
    }

    @Test
    public void transformFlatmap2() {
        List<List<Integer>> ints = new ArrayList<>();
        List<Integer> first = List.of(1, 2, 3, 4, 5, 6);
        List<Integer> second = List.of(3, 6, 9, 12);
        ints.add(first);
        ints.add(second);

        var numberFlux = Flux.fromIterable(ints)
                .flatMap(Flux::fromIterable)
                .log();
        StepVerifier.create(numberFlux)
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    public void transformFlatmapParallel() {
        var numberFlux = Flux.fromIterable(numbers)
                .window(2)
                .flatMap((s) ->
                        s.map(this::converToList)
                                .subscribeOn(parallel()))
                .flatMap(Flux::fromIterable)
                .log();
        StepVerifier.create(numberFlux)
                .expectNextCount(8)
                .verifyComplete();
    }

    @Test
    public void transformFlatmapParallelMaintainOrder() {
        var numberFlux = Flux.fromIterable(numbers)
                .window(2)
                .flatMapSequential((s) ->
                        s.map(this::converToList)
                                .subscribeOn(parallel())
                ).flatMap(Flux::fromIterable)
                .log();
        StepVerifier.create(numberFlux)
                .expectNextCount(8)
                .verifyComplete();
    }

    private List<String> converToList(String s) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Arrays.asList(s, "just add something");


    }

}
