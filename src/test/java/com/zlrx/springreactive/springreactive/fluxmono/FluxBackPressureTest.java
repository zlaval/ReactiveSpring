package com.zlrx.springreactive.springreactive.fluxmono;

import org.junit.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxBackPressureTest {

    @Test
    public void backPressureTest() {
        var finiteFlux = Flux.range(1, 10)
                .log();

        StepVerifier.create(finiteFlux)
                .expectSubscription()
                .thenRequest(1)
                .expectNext(1)
                .thenRequest(2)
                .expectNext(2, 3)
                .thenCancel()
                .verify();
    }

    @Test
    public void backPressure() {
        var finiteFlux = Flux.range(1, 10)
                .log();
        finiteFlux.subscribe(System.out::println, System.out::println, () -> System.out.println("DONE"), s -> s.request(2));
    }

    @Test
    public void backPressureCancel() {
        var finiteFlux = Flux.range(1, 10)
                .log();
        finiteFlux.subscribe(
                System.out::println,
                System.out::println,
                () -> System.out.println("DONE"),
                Subscription::cancel
        );
    }

    @Test
    public void backPressureCustomize() {
        var finiteFlux = Flux.range(1, 10)
                .log();
        finiteFlux.subscribe(
                new BaseSubscriber<>() {
                    @Override
                    protected void hookOnNext(Integer value) {
                        request(1);
                        System.out.println(value);
                        if (value == 4) {
                            cancel();
                        }
                    }
                }
        );
    }


}
