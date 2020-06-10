package com.curtisnewbie.tacocloud;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    // this is implemented by the Spring automatically
    User findByUsername(String username);
}
