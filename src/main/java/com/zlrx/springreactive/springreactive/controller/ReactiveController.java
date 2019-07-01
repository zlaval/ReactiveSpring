package com.zlrx.springreactive.springreactive.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/api/v1")
public class ReactiveController {

    @GetMapping(value = "/numbers", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Integer> getNumbers() {
        return Flux.range(0, 4);
        // .delayElements(Duration.ofSeconds(1));
    }

    @GetMapping(value = "/infinite-numbers", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Long> getInfiniteNumbers() {
        return Flux.interval(Duration.ofSeconds(1));
    }

    @GetMapping(value = "/mono-number")
    public Mono<Integer> getMonoNumber() {
        return Mono.just(0).log();
    }

}
