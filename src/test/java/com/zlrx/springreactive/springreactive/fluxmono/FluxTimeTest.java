package com.zlrx.springreactive.springreactive.fluxmono;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxTimeTest {

    @Test
    public void infiniteSequence() throws InterruptedException {
        var infiniteFlux = Flux.interval(Duration.ofMillis(200)).log();
        infiniteFlux.subscribe(System.out::println);
        Thread.sleep(3000);
    }

    @Test
    public void infiniteSequenceTest() {
        var finiteFlux = Flux.interval(Duration.ofMillis(200))
                .take(3)
                .log();

        StepVerifier.create(finiteFlux)
                .expectSubscription()
                .expectNextCount(3)
                .verifyComplete();
    }


    @Test
    public void infiniteSequenceMap() {
        var infiniteFlux = Flux.interval(Duration.ofMillis(200))
                .delayElements(Duration.ofSeconds(1))
                .map(Long::intValue)
                .take(3)
                .log();

        StepVerifier.create(infiniteFlux)
                .expectSubscription()
                .expectNextCount(3)
                .verifyComplete();
    }


}
