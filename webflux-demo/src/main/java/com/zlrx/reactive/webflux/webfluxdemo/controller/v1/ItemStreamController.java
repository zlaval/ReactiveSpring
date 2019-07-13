package com.zlrx.reactive.webflux.webfluxdemo.controller.v1;


import com.zlrx.reactive.webflux.webfluxdemo.document.ItemCapped;
import com.zlrx.reactive.webflux.webfluxdemo.repository.ItemCappedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@RestController
@RequestMapping("/api/v1/item-stream")
public class ItemStreamController {

    @Autowired
    private ItemCappedRepository itemCappedRepository;

    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<ItemCapped> getItemsStream() {
        return itemCappedRepository.findItemsBy();
    }

}
