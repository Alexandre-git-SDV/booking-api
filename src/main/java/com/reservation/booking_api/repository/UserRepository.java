package com.reservation.booking_api.repository;

import com.reservation.booking_api.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    // MongodbRepository donne des requetes CRUD de base

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
