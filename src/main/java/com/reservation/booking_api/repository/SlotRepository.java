package com.reservation.booking_api.repository;

import com.reservation.booking_api.entity.Slot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SlotRepository extends MongoRepository<Slot, String> {

    List<Slot> findByUserId(String userId);

    List<Slot> findByRoomId(String roomId);

    // Détection de conflit : créneaux qui chevauchent la plage demandée
    @Query("{ 'roomId': ?0, 'startTime': { $lt: ?2 }, 'endTime': { $gt: ?1 } }")
    List<Slot> findConflictingSlots(String roomId, LocalDateTime startTime, LocalDateTime endTime);
}
