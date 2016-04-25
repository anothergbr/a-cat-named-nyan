package com.gbr.nyan.domain;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
    User findByOpenIdUrl(String openIdUrl);

    User findByEmail(String email);
}
