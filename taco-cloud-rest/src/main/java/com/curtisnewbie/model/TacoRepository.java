package com.curtisnewbie.model;

import java.util.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco, Long> {

    public List<Taco> findAll(Pageable pageable);
}
