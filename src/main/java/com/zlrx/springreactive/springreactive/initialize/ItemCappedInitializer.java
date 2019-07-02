package com.zlrx.springreactive.springreactive.initialize;

import com.zlrx.springreactive.springreactive.document.ItemCapped;
import com.zlrx.springreactive.springreactive.repository.ItemCappedRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.time.Duration;

@Slf4j
@Component
//@Profile("!test")
public class ItemCappedInitializer {

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private ItemCappedRepository itemCappedRepository;

    @PostConstruct
    public void init() {
        createCappedCollection();
        cappedItemDataSetup();
    }

    private void createCappedCollection() {
        if (mongoOperations.collectionExists(ItemCapped.class)) {
            mongoOperations.dropCollection(ItemCapped.class);
        }
        mongoOperations.createCollection(ItemCapped.class,
                CollectionOptions.empty().maxDocuments(20).size(5000).capped()
        );
    }

    private void cappedItemDataSetup() {
        Flux<ItemCapped> itemCappedFlux = Flux.interval(Duration.ofSeconds(1))
                .map(i -> new ItemCapped(null, "Item " + i, i));
        itemCappedRepository.insert(itemCappedFlux)
                .subscribe(item -> log.info("Insert item {}", item));
    }

}
