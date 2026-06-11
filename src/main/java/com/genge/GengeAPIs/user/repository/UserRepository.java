package com.genge.GengeAPIs.user.repository;

import com.genge.GengeAPIs.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    Optional<User> findByEmail(String email);

//    Optional<User> findByUsername(String username);

    Optional<User> findByPhone(String phone);

//    Optional<User> findByNidaNumber(String nidaNumber);

//    Optional<User> findByIdNumber(String idNumber);
}
