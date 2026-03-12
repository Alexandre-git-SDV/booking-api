package com.reservation.booking_api.dto;

import com.reservation.booking_api.dto.request.RegisterRequest;
import com.reservation.booking_api.dto.request.LoginRequest;
import com.reservation.booking_api.dto.request.RoomRequest;
import com.reservation.booking_api.dto.request.SlotRequest;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class DtoValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void registerRequest_valid() {
        RegisterRequest req = new RegisterRequest();
        req.setFirstName("John");
        req.setLastName("Doe");
        req.setEmail("john@test.com");
        req.setPassword("password123");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(req);
        assertThat(violations).isEmpty();
    }

    @Test
    void registerRequest_invalidEmail() {
        RegisterRequest req = new RegisterRequest();
        req.setFirstName("John");
        req.setLastName("Doe");
        req.setEmail("not-an-email");
        req.setPassword("password123");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(req);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void registerRequest_blankFields() {
        RegisterRequest req = new RegisterRequest();

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(req);
        assertThat(violations).hasSizeGreaterThanOrEqualTo(3);
    }

    @Test
    void loginRequest_valid() {
        LoginRequest req = new LoginRequest();
        req.setEmail("john@test.com");
        req.setPassword("password123");

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(req);
        assertThat(violations).isEmpty();
    }

    @Test
    void roomRequest_invalid_negativeCapacity() {
        RoomRequest req = new RoomRequest();
        req.setName("Salle A");
        req.setCapacity(-1);

        Set<ConstraintViolation<RoomRequest>> violations = validator.validate(req);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void slotRequest_valid() {
        SlotRequest req = new SlotRequest();
        req.setRoomId("room1");
        req.setStartTime(LocalDateTime.now().plusHours(1));
        req.setEndTime(LocalDateTime.now().plusHours(2));

        Set<ConstraintViolation<SlotRequest>> violations = validator.validate(req);
        assertThat(violations).isEmpty();
    }
}
