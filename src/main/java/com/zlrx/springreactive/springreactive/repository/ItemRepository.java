package com.zlrx.springreactive.springreactive.repository;

import com.zlrx.springreactive.springreactive.document.Item;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ItemRepository extends ReactiveMongoRepository<Item, String> {

    Flux<Item> findByDescription(String description);

}
