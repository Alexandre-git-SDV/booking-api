package com.reservation.booking_api.repository;

import com.reservation.booking_api.entity.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends MongoRepository<Room, String> {

    boolean existsByName(String name);

    List<Room> findByAvailableTrue();
}

