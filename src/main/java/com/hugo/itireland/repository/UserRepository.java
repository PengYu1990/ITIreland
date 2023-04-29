package com.hugo.itireland.repository;

import com.hugo.itireland.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
