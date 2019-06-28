package com.zlrx.springreactive.springreactive.fluxmono;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class HotColdPublisherTest {


    @Test
    public void coldPublisherTest() throws InterruptedException {
        var stringFlux = Flux.just("A", "B", "C", "D", "E", "F")
                .delayElements(Duration.ofSeconds(1));

        stringFlux.subscribe(e -> System.out.println("S1 emits: " + e));
        Thread.sleep(2000);
        stringFlux.subscribe(e -> System.out.println("S2 emits: " + e));
        Thread.sleep(7000);
    }


    @Test
    public void hotPublisherTest() throws InterruptedException {
        var stringFlux = Flux.just("A", "B", "C", "D", "E", "F")
                .delayElements(Duration.ofSeconds(1))
                .publish();
        stringFlux.connect();
        stringFlux.subscribe(e -> System.out.println("S1 emits: " + e));
        Thread.sleep(3000);
        stringFlux.subscribe(e -> System.out.println("S2 emits: " + e));
        Thread.sleep(7000);
    }


}
