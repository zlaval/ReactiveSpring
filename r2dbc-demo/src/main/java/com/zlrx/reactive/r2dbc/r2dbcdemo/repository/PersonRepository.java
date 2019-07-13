package com.zlrx.reactive.r2dbc.r2dbcdemo.repository;

import com.zlrx.reactive.r2dbc.r2dbcdemo.domain.Person;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

public interface PersonRepository extends R2dbcRepository<Person, Long> {

}
