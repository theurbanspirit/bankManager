package com.bank.manager.repositories;

import com.bank.manager.models.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Long> {

    boolean existsByUsername(String username);

    AppUser findByUsername(String username);

    void deleteByUsername(String username);

}
