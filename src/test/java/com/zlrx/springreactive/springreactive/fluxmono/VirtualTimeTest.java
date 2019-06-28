package com.zlrx.springreactive.springreactive.fluxmono;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Duration;

public class VirtualTimeTest {

    @Test
    public void testWithoutVirtualTime() {
        var longFlux = Flux.interval(Duration.ofSeconds(1))
                .take(3)
                .log();
        StepVerifier.create(longFlux)
                .expectSubscription()
                .expectNext(0L, 1L, 2L)
                .verifyComplete();
    }

    @Test
    public void testWithVirtualTime() {
        VirtualTimeScheduler.getOrSet();
        var longFlux = Flux.interval(Duration.ofSeconds(1))
                .take(3)
                .log();
        StepVerifier.withVirtualTime(() -> longFlux)
                .expectSubscription()
                .thenAwait(Duration.ofSeconds(3))
                .expectNext(0L, 1L, 2L)
                .verifyComplete();
    }


}
