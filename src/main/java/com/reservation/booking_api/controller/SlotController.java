package com.reservation.booking_api.controller;

import com.reservation.booking_api.dto.request.SlotRequest;
import com.reservation.booking_api.dto.response.SlotResponse;
import com.reservation.booking_api.service.SlotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/slots")
@RequiredArgsConstructor
@Tag(name = "Créneaux", description = "Gestion des réservations")
public class SlotController {

    private final SlotService slotService;

    @GetMapping
    @Operation(summary = "Lister tous les créneaux")
    public ResponseEntity<List<SlotResponse>> getAllSlots() {
        return ResponseEntity.ok(slotService.getAllSlots());
    }

    @GetMapping("/room/{roomId}")
    @Operation(summary = "Créneaux par salle")
    public ResponseEntity<List<SlotResponse>> getSlotsByRoom(@PathVariable String roomId) {
        return ResponseEntity.ok(slotService.getSlotsByRoom(roomId));
    }

    @GetMapping("/my-slots")
    @Operation(summary = "Mes réservations")
    public ResponseEntity<List<SlotResponse>> getMySlots() {
        return ResponseEntity.ok(slotService.getMySlots());
    }

    @PostMapping
    @Operation(summary = "Réserver un créneau")
    public ResponseEntity<SlotResponse> createSlot(@Valid @RequestBody SlotRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(slotService.createSlot(request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Annuler une réservation")
    public ResponseEntity<Void> deleteSlot(@PathVariable String id) {
        slotService.deleteSlot(id);
        return ResponseEntity.noContent().build();
    }
}
