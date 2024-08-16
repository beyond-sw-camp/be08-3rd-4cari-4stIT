package com.fourstit.pocari.repository;

import com.fourstit.pocari.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
