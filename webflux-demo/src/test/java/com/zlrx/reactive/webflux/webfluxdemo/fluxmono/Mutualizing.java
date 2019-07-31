package com.zlrx.reactive.webflux.webfluxdemo.fluxmono;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class Mutualizing {

    @Test
    public void transform() {
        Function<Flux<String>, Flux<String>> filterAndMap =
                f -> f.filter(color -> !color.equals("orange"))
                        .map(String::toUpperCase);

        Flux.fromIterable(Arrays.asList("blue", "green", "orange", "purple"))
                .doOnNext(System.out::println)
                .transform(filterAndMap)
                .subscribe(d -> System.out.println("Subscriber to Transformed MapAndFilter: " + d));
    }

    @Test
    public void compose() {
        AtomicInteger ai = new AtomicInteger();
        Function<Flux<String>, Flux<String>> filterAndMap = f -> {
            if (ai.incrementAndGet() == 1) {
                return f.filter(color -> !color.equals("orange"))
                        .map(String::toUpperCase);
            }
            return f.filter(color -> !color.equals("purple"))
                    .map(String::toUpperCase);
        };

        Flux<String> composedFlux =
                Flux.fromIterable(Arrays.asList("blue", "green", "orange", "purple"))
                        .doOnNext(System.out::println)
                        .compose(filterAndMap);

        composedFlux.subscribe(d -> System.out.println("Subscriber 1 to Composed MapAndFilter :" + d));
        composedFlux.subscribe(d -> System.out.println("Subscriber 2 to Composed MapAndFilter: " + d));
    }

    @Test
    public void parallelFlux() throws InterruptedException {
        Flux.range(1, 10)
                .parallel(4)
                .runOn(Schedulers.parallel())
                .subscribe(i -> System.out.println(Thread.currentThread().getName() + " -> " + i));
        Thread.sleep(2000);
    }

}
