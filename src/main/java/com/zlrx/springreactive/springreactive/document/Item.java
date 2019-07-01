package com.zlrx.springreactive.springreactive.document;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Item implements Serializable {

    @Id
    private String id;
    private String description;
    private Long price;
}
