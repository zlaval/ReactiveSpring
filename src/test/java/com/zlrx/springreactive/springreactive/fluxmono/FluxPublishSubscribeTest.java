package com.zlrx.springreactive.springreactive.fluxmono;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class FluxPublishSubscribeTest {

    @Test
    public void publishOn() throws InterruptedException {
        Scheduler scheduler = Schedulers.elastic();
        Flux.range(1, 4)
                .map(i -> {
                    System.out.println("before publishOn, use thread " + Thread.currentThread().getName());
                    return i * 2;
                })
                .publishOn(scheduler)
                .map(i -> {
                    System.out.println("after publishOn, use thread " + Thread.currentThread().getName());
                    return i * 2;
                })
                .subscribe(v -> System.out.println("Subscribe " + v + " on thread " + Thread.currentThread().getName()));

        Thread.sleep(1000);

    }

    @Test
    public void subscribeOn() throws InterruptedException {
        Scheduler scheduler = Schedulers.elastic();
        Flux.range(1, 4)
                .map(i -> {
                    System.out.println("before subscribeOn, use thread " + Thread.currentThread().getName());
                    return i * 2;
                })
                .subscribeOn(scheduler)
                .map(i -> {
                    System.out.println("after subscribeOn, use thread " + Thread.currentThread().getName());
                    return i * 2;
                })
                .subscribe();

        Thread.sleep(1000);
    }

    @Test
    public void subscribeOnPublishOnSameScheduler() throws InterruptedException {
        Scheduler scheduler = Schedulers.elastic();
        Flux.range(1, 4)
                .map(i -> {
                    System.out.println("before subscribeOn before publishOn, use thread " + Thread.currentThread().getName());
                    return i * 2;
                })
                .subscribeOn(scheduler)
                .map(i -> {
                    System.out.println("after subscribeOn before publishOn, use thread " + Thread.currentThread().getName());
                    return i * 2;
                })
                .publishOn(scheduler)
                .map(i -> {
                    System.out.println("after subscribeOn after publishOn, use thread " + Thread.currentThread().getName());
                    return i * 2;
                })
                .subscribe();
        Thread.sleep(1000);
    }

    @Test
    public void publishOnSubscribeOnSameScheduler() throws InterruptedException {
        Scheduler scheduler = Schedulers.elastic();
        Flux.range(1, 4)
                .map(i -> {
                    System.out.println("before subscribeOn before publishOn, use thread " + Thread.currentThread().getName());
                    return i * 2;
                })
                .publishOn(scheduler)
                .map(i -> {
                    System.out.println("before subscribeOn after publishOn, use thread " + Thread.currentThread().getName());
                    return i * 2;
                })
                .subscribeOn(scheduler)
                .map(i -> {
                    System.out.println("after subscribeOn after publishOn, use thread " + Thread.currentThread().getName());
                    return i * 2;
                })
                .subscribe();
        Thread.sleep(1000);
    }


    @Test
    public void doublePublishOnSubscribeOnSameScheduler() throws InterruptedException {
        Scheduler scheduler = Schedulers.elastic();
        Flux.range(1, 4)
                .map(i -> {
                    System.out.println("before subscribeOn1 before publishOn1, use thread " + Thread.currentThread().getName());
                    return i * 2;
                })
                .publishOn(scheduler)
                .map(i -> {
                    System.out.println("before subscribeOn1 after publishOn1, use thread " + Thread.currentThread().getName());
                    return i * 2;
                })
                .subscribeOn(scheduler)
                .map(i -> {
                    System.out.println("after subscribeOn1 after publishOn1, use thread " + Thread.currentThread().getName());
                    return i * 2;
                })
                .publishOn(scheduler)
                .map(i -> {
                    System.out.println("before subscribeOn2 after publishOn2, use thread " + Thread.currentThread().getName());
                    return i;
                })
                .subscribeOn(scheduler)
                .map(i -> {
                    System.out.println("after subscribeOn2 after publishOn2, use thread " + Thread.currentThread().getName());
                    return i;
                })
                .subscribe();
        Thread.sleep(1000);
    }

    @Test
    public void subscribeOnPublishOnDifferentScheduler() throws InterruptedException {
        Scheduler scheduler = Schedulers.elastic();
        Scheduler scheduler2 = Schedulers.parallel();
        Flux.range(1, 4)
                .map(i -> {
                    System.out.println("before subscribeOn before publishOn, use thread " + Thread.currentThread().getName());
                    return i * 2;
                })
                .subscribeOn(scheduler)
                .map(i -> {
                    System.out.println("after subscribeOn before publishOn, use thread " + Thread.currentThread().getName());
                    return i * 2;
                })
                .publishOn(scheduler2)
                .map(i -> {
                    System.out.println("after subscribeOn after publishOn, use thread " + Thread.currentThread().getName());
                    return i * 2;
                })
                .subscribe();
        Thread.sleep(1000);
    }

    @Test
    public void subscribeOnPublishOnDifferentcheduler() throws InterruptedException {
        Scheduler scheduler = Schedulers.elastic();
        Scheduler scheduler2 = Schedulers.parallel();
        Flux.range(1, 4)
                .map(i -> {
                    System.out.println("before subscribeOn before publishOn, use thread " + Thread.currentThread().getName());
                    return i * 2;
                })
                .subscribeOn(scheduler2)
                .map(i -> {
                    System.out.println("after subscribeOn before publishOn, use thread " + Thread.currentThread().getName());
                    return i * 2;
                })
                .publishOn(scheduler)
                .map(i -> {
                    System.out.println("after subscribeOn after publishOn, use thread " + Thread.currentThread().getName());
                    return i * 2;
                })
                .subscribe();
        Thread.sleep(1000);
    }

}
