package org.running.domain.user.repository;

import java.util.Optional;
import org.running.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // email로 사용자 정보 가져옴
    Optional<User> findByName(String name);
}
