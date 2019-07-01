package com.zlrx.springreactive.springreactive.repository;

import com.zlrx.springreactive.springreactive.document.Item;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@DataMongoTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DirtiesContext
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    private List<Item> items = List.of(
            new Item(null, "Mobile", 200L),
            new Item(null, "Notebook", 300L),
            new Item(null, "Notebook", 400L),
            new Item("mockid", "TV", 150L)
    );

    @Before
    public void setUp() {
        itemRepository.deleteAll()
                .thenMany(Flux.fromIterable(items))
                .flatMap(itemRepository::save)
                .doOnNext(System.out::println)
                .blockLast();
    }

    @Test
    public void getAllItems() {
        Flux<Item> itemFlux = itemRepository.findAll();
        StepVerifier.create(itemFlux.log())
                .expectSubscription()
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    public void getItemById() {
        Mono<Item> itemMono = itemRepository.findById("mockid");
        StepVerifier.create(itemMono)
                .expectSubscription()
                .expectNextMatches(item -> item.equals(new Item("mockid", "TV", 150L)))
                .verifyComplete();

    }

    @Test
    public void findItemByDescription() {
        Flux<Item> itemMono = itemRepository.findByDescription("Notebook");
        StepVerifier.create(itemMono)
                .expectSubscription()
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    public void saveItem() {
        var item = new Item("DEF", "Monitor", 50L);
        Mono<Item> persistedItem = itemRepository.save(item);
        StepVerifier.create(persistedItem)
                .expectSubscription()
                .expectNextMatches(savedItem -> savedItem.equals(item))
                .verifyComplete();
    }

    @Test
    public void updateItem() {
        Flux<Item> updatedItems = itemRepository.findByDescription("Mobile")
                .map(item -> {
                    item.setPrice(220L);
                    return item;
                })
                .flatMap(item -> itemRepository.save(item));

        StepVerifier.create(updatedItems)
                .expectSubscription()
                .expectNextMatches(item -> item.getPrice().equals(220L))
                .verifyComplete();
    }


    @Test
    public void deleteItem() {
        Mono<Void> deletedItem = itemRepository.findById("mockid")
                .map(Item::getId)
                .flatMap(id -> itemRepository.deleteById(id));

        StepVerifier.create(deletedItem)
                .expectSubscription()
                .verifyComplete();

        StepVerifier.create(itemRepository.findAll().log("The new Item List: "))
                .expectSubscription()
                .expectNextCount(3)
                .verifyComplete();
    }

}
