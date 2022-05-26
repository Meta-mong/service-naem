package com.metamong.metaticket.repository.user;


import com.metamong.metaticket.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    User findByName(String name);

    User findByEmail(String email);

    User findByNumber(String number);

}
