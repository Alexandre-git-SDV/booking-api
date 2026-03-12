package com.reservation.booking_api.controller;

import com.reservation.booking_api.dto.request.RoomRequest;
import com.reservation.booking_api.dto.response.RoomResponse;
import com.reservation.booking_api.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
@Tag(name = "Salles", description = "Gestion des salles")
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    @Operation(summary = "Lister toutes les salles")
    public ResponseEntity<List<RoomResponse>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/available")
    @Operation(summary = "Lister les salles disponibles")
    public ResponseEntity<List<RoomResponse>> getAvailableRooms() {
        return ResponseEntity.ok(roomService.getAvailableRooms());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Détail d'une salle")
    public ResponseEntity<RoomResponse> getRoomById(@PathVariable String id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @PostMapping
    @Operation(summary = "Créer une salle (ADMIN)")
    public ResponseEntity<RoomResponse> createRoom(@Valid @RequestBody RoomRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.createRoom(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une salle (ADMIN)")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable String id, @Valid @RequestBody RoomRequest request) {
        return ResponseEntity.ok(roomService.updateRoom(id, request));
    }

    @PatchMapping("/{id}/toggle")
    @Operation(summary = "Activer/désactiver une salle (ADMIN)")
    public ResponseEntity<RoomResponse> toggleAvailability(@PathVariable String id) {
        return ResponseEntity.ok(roomService.toggleAvailability(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une salle (ADMIN)")
    public ResponseEntity<Void> deleteRoom(@PathVariable String id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}
