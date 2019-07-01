package com.zlrx.springreactive.springreactive.controller.v1;


import com.zlrx.springreactive.springreactive.document.Item;
import com.zlrx.springreactive.springreactive.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
@RestController
@RequestMapping("/api/v1/item")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public Flux<Item> getItems() {
        return itemRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Item>> getItem(@PathVariable("id") String id) {
        return itemRepository.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Item> addItem(@RequestBody Item item) {
        return itemRepository.save(item);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteItem(@PathVariable("id") String id) {
        return itemRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Item>> updateItem(@PathVariable("id") String id, @RequestBody Item item) {
        return itemRepository.findById(id)
                .flatMap(currentItem -> {
                    currentItem.setPrice(item.getPrice());
                    currentItem.setDescription(item.getDescription());
                    return itemRepository.save(currentItem);
                }).map(ResponseEntity::ok)
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
