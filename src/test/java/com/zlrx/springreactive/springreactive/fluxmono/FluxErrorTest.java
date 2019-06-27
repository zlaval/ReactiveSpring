package com.zlrx.springreactive.springreactive.fluxmono;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxErrorTest {

    @Test
    public void fluxErrorHandling() {
        var stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("EXC")))
                .concatWith(Flux.just("D"))
                .log();
        StepVerifier.create(stringFlux)
                .expectSubscription()
                .expectNextCount(3)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    public void fluxErrorHandlingResume() {
        var stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("EXC")))
                .concatWith(Flux.just("D"))
                .onErrorResume((e) -> {
                    System.out.println(e);
                    return Flux.just("default", "default2");
                })
                .log();
        StepVerifier.create(stringFlux)
                .expectSubscription()
                .expectNextCount(3)
                .expectNext("default", "default2")
                .verifyComplete();
    }

    @Test
    public void fluxErrorHandlingErrorReturn() {
        var stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("EXC")))
                .concatWith(Flux.just("D"))
                .onErrorReturn("default")
                .log();
        StepVerifier.create(stringFlux)
                .expectSubscription()
                .expectNextCount(3)
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    public void fluxErrorHandlingErrorMap() {
        var stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("EXC")))
                .concatWith(Flux.just("D"))
                .onErrorMap(CustomException::new)
                .log();
        StepVerifier.create(stringFlux)
                .expectSubscription()
                .expectNextCount(3)
                .verifyError(CustomException.class);
    }

    @Test
    public void fluxErrorHandlingRetry() {
        var stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("EXC")))
                .concatWith(Flux.just("D"))
                .retry(2)
                .log();
        StepVerifier.create(stringFlux)
                .expectSubscription()
                .expectNextCount(9)
                .verifyError(RuntimeException.class);
    }

    @Test
    public void fluxErrorHandlingRetryBackOff() {
        var stringFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("EXC")))
                .concatWith(Flux.just("D"))
                .retryBackoff(2, Duration.ofSeconds(3))
                .log();
        StepVerifier.create(stringFlux)
                .expectSubscription()
                .expectNextCount(9)
                .verifyError(RuntimeException.class);
    }

}
