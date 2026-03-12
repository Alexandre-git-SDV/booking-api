package com.reservation.booking_api.repository;

import com.reservation.booking_api.entity.User;
import com.reservation.booking_api.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@test.com")
                .password("password123")
                .role(Role.USER)
                .build();
        userRepository.save(user);
    }

    @Test
    void shouldFindUserByEmail() {
        Optional<User> found = userRepository.findByEmail("john@test.com");
        assertThat(found).isPresent();
        assertThat(found.get().getFirstName()).isEqualTo("John");
    }

    @Test
    void shouldReturnEmptyWhenEmailNotFound() {
        Optional<User> found = userRepository.findByEmail("unknown@test.com");
        assertThat(found).isEmpty();
    }

    @Test
    void shouldReturnTrueWhenEmailExists() {
        assertThat(userRepository.existsByEmail("john@test.com")).isTrue();
    }

    @Test
    void shouldReturnFalseWhenEmailNotExists() {
        assertThat(userRepository.existsByEmail("unknown@test.com")).isFalse();
    }
}