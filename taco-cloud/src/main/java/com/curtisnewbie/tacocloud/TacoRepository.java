package com.curtisnewbie.tacocloud;

import org.springframework.data.repository.CrudRepository;

// <T,V>, T is the Entity, V is the primaryKey
public interface TacoRepository extends CrudRepository<Taco, Long> {

    // Taco save(Taco taco);

}
