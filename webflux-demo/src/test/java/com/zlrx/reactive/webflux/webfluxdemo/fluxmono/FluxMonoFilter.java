package com.zlrx.reactive.webflux.webfluxdemo.fluxmono;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FluxMonoFilter {


    @Test
    public void test() {
        List<Integer> filter = Arrays.asList(3, 6, 8);
        List<Integer> filterable1 = Arrays.asList(3, 7, 9);
        List<Integer> filterable2 = Arrays.asList(1, 4, 5);
        List<Integer> filterable3 = Arrays.asList(1, 4, 8);

        Mono<List<Integer>> fm = Mono.just(filter);
        Flux<List<Integer>> ff = Flux.just(filterable1, filterable2, filterable3);

        Mono<List<Integer>> x = ff.flatMapIterable(i -> i).distinct().collectList();

        x.zipWith(fm).map(t -> t.getT1().stream().filter(a -> t.getT2().contains(a)).collect(Collectors.toList())).subscribe(l -> System.out.println(l));


    }
}
