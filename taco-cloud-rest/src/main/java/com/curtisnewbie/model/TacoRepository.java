package com.curtisnewbie.model;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco, Long> {

    public Iterable<Taco> findAll(Pageable pageable);
}
