package com.reservation.booking_api.service;

import com.reservation.booking_api.dto.request.RoomRequest;
import com.reservation.booking_api.dto.response.RoomResponse;
import com.reservation.booking_api.entity.Room;
import com.reservation.booking_api.exception.AlreadyExistsException;
import com.reservation.booking_api.exception.ResourceNotFoundException;
import com.reservation.booking_api.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public List<RoomResponse> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<RoomResponse> getAvailableRooms() {
        return roomRepository.findByAvailableTrue().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public RoomResponse getRoomById(String id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Salle non trouvée avec l'ID : " + id));
        return toResponse(room);
    }

    public RoomResponse createRoom(RoomRequest request) {
        if (roomRepository.existsByName(request.getName())) {
            throw new AlreadyExistsException("Une salle avec ce nom existe déjà");
        }

        Room room = Room.builder()
                .name(request.getName())
                .capacity(request.getCapacity())
                .description(request.getDescription())
                .build();

        Room saved = roomRepository.save(room);
        return toResponse(saved);
    }

    public RoomResponse updateRoom(String id, RoomRequest request) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Salle non trouvée avec l'ID : " + id));

        room.setName(request.getName());
        room.setCapacity(request.getCapacity());
        room.setDescription(request.getDescription());

        Room updated = roomRepository.save(room);
        return toResponse(updated);
    }

    public RoomResponse toggleAvailability(String id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Salle non trouvée avec l'ID : " + id));

        room.setAvailable(!room.isAvailable());
        Room updated = roomRepository.save(room);
        return toResponse(updated);
    }

    public void deleteRoom(String id) {
        if (!roomRepository.existsById(id)) {
            throw new ResourceNotFoundException("Salle non trouvée avec l'ID : " + id);
        }
        roomRepository.deleteById(id);
    }

    private RoomResponse toResponse(Room room) {
        return RoomResponse.builder()
                .id(room.getId())
                .name(room.getName())
                .capacity(room.getCapacity())
                .description(room.getDescription())
                .available(room.isAvailable())
                .build();
    }
}
