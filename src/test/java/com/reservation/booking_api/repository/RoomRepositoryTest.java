package com.reservation.booking_api.repository;

import com.reservation.booking_api.entity.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @BeforeEach
    void setUp() {
        roomRepository.deleteAll();

        roomRepository.save(Room.builder()
                .name("Salle A")
                .capacity(10)
                .available(true)
                .build());

        roomRepository.save(Room.builder()
                .name("Salle B")
                .capacity(5)
                .available(false)
                .build());
    }

    @Test
    void shouldFindAvailableRooms() {
        List<Room> rooms = roomRepository.findByAvailableTrue();
        assertThat(rooms).hasSize(1);
        assertThat(rooms.get(0).getName()).isEqualTo("Salle A");
    }

    @Test
    void shouldFindAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        assertThat(rooms).hasSize(2);
    }
}
