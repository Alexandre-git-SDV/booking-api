package com.reservation.booking_api.repository;

import com.reservation.booking_api.entity.Slot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class SlotRepositoryTest {

    @Autowired
    private SlotRepository slotRepository;

    @BeforeEach
    void setUp() {
        slotRepository.deleteAll();

        slotRepository.save(Slot.builder()
                .roomId("room1")
                .userId("user1")
                .startTime(LocalDateTime.of(2025, 1, 15, 9, 0))
                .endTime(LocalDateTime.of(2025, 1, 15, 10, 0))
                .build());

        slotRepository.save(Slot.builder()
                .roomId("room1")
                .userId("user2")
                .startTime(LocalDateTime.of(2025, 1, 15, 14, 0))
                .endTime(LocalDateTime.of(2025, 1, 15, 15, 0))
                .build());

        slotRepository.save(Slot.builder()
                .roomId("room2")
                .userId("user1")
                .startTime(LocalDateTime.of(2025, 1, 15, 9, 0))
                .endTime(LocalDateTime.of(2025, 1, 15, 10, 0))
                .build());
    }

    @Test
    void shouldFindSlotsByRoomId() {
        List<Slot> slots = slotRepository.findByRoomId("room1");
        assertThat(slots).hasSize(2);
    }

    @Test
    void shouldFindSlotsByUserId() {
        List<Slot> slots = slotRepository.findByUserId("user1");
        assertThat(slots).hasSize(2);
    }

    @Test
    void shouldReturnEmptyForUnknownRoom() {
        List<Slot> slots = slotRepository.findByRoomId("unknown");
        assertThat(slots).isEmpty();
    }
}
