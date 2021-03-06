package com.zlrx.reactive.webflux.webfluxdemo.document;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class ItemCapped {

    @Id
    private String id;
    private String description;
    private Long price;

}
