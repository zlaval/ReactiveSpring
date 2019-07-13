package com.zlrx.reactive.r2dbc.r2dbcdemo.domain;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("person")
@Getter
@Setter
public class Person {

    @Id
    private Long id;
    private String name;

}
