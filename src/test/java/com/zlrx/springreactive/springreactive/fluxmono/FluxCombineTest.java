package com.zlrx.springreactive.springreactive.fluxmono;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxCombineTest {

    @Test
    public void combineWithMerge() {
        var abc = Flux.just("A", "B", "C");
        var def = Flux.just("D", "E", "F");
        var merged = Flux.merge(abc, def).log();
        StepVerifier.create(merged)
                .expectSubscription()
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    public void combineWithMergeDelayed() {
        var abc = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
        var def = Flux.just("D", "E", "F").delayElements(Duration.ofSeconds(1));
        var merged = Flux.merge(abc, def).log();
        StepVerifier.create(merged)
                .expectSubscription()
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    public void combineWithConcat() {
        var abc = Flux.just("A", "B", "C");
        var def = Flux.just("D", "E", "F");
        var merged = Flux.concat(abc, def).log();
        StepVerifier.create(merged)
                .expectSubscription()
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    public void combineWithConcatDelayed() {
        var abc = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
        ;
        var def = Flux.just("D", "E", "F").delayElements(Duration.ofSeconds(1));
        ;
        var merged = Flux.concat(abc, def).log();
        StepVerifier.create(merged)
                .expectSubscription()
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }


    @Test
    public void combineWithZip() {
        var abc = Flux.just("A", "B", "C");
        var def = Flux.just("D", "E", "F");
        var merged = Flux.zip(abc, def, String::concat).log();
        StepVerifier.create(merged)
                .expectSubscription()
                .expectNext("AD", "BE", "CF")
                .verifyComplete();
    }


}
