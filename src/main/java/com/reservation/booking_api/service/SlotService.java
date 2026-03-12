package com.reservation.booking_api.service;

import com.reservation.booking_api.dto.request.SlotRequest;
import com.reservation.booking_api.dto.response.SlotResponse;
import com.reservation.booking_api.entity.Room;
import com.reservation.booking_api.entity.Slot;
import com.reservation.booking_api.entity.User;
import com.reservation.booking_api.exception.BadRequestException;
import com.reservation.booking_api.exception.ForbiddenException;
import com.reservation.booking_api.exception.ResourceNotFoundException;
import com.reservation.booking_api.exception.SlotConflictException;
import com.reservation.booking_api.repository.RoomRepository;
import com.reservation.booking_api.repository.SlotRepository;
import com.reservation.booking_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SlotService {

    private final SlotRepository slotRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public List<SlotResponse> getAllSlots() {
        return slotRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public List<SlotResponse> getSlotsByRoom(String roomId) {
        return slotRepository.findByRoomId(roomId).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<SlotResponse> getMySlots() {
        User currentUser = getCurrentUser();
        return slotRepository.findByUserId(currentUser.getId()).stream()
                .map(this::toResponse)
                .toList();
    }

    public SlotResponse createSlot(SlotRequest request) {
        // Validation des dates
        if (request.getStartTime().isAfter(request.getEndTime())) {
            throw new BadRequestException("L'heure de début doit être avant l'heure de fin");
        }

        if (request.getStartTime().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Impossible de réserver un créneau dans le passé");
        }

        // Vérifier que la salle existe et est disponible
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Salle non trouvée"));

        if (!room.isAvailable()) {
            throw new BadRequestException("Cette salle n'est pas disponible");
        }

        // Vérifier les conflits
        List<Slot> conflicts = slotRepository.findConflictingSlots(
                request.getRoomId(),
                request.getStartTime(),
                request.getEndTime()
        );

        if (!conflicts.isEmpty()) {
            throw new SlotConflictException("Ce créneau chevauche une réservation existante");
        }

        User currentUser = getCurrentUser();

        Slot slot = Slot.builder()
                .roomId(request.getRoomId())
                .userId(currentUser.getId())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .build();

        Slot saved = slotRepository.save(slot);
        return toResponse(saved);
    }

    public void deleteSlot(String id) {
        Slot slot = slotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Créneau non trouvé"));

        User currentUser = getCurrentUser();

        // Seul le propriétaire ou un admin peut supprimer
        boolean isOwner = slot.getUserId().equals(currentUser.getId());
        boolean isAdmin = currentUser.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isOwner && !isAdmin) {
            throw new ForbiddenException("Vous ne pouvez supprimer que vos propres réservations");
        }

        slotRepository.deleteById(id);
    }

    // ===== HELPERS =====

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
    }

    private SlotResponse toResponse(Slot slot) {
        Room room = roomRepository.findById(slot.getRoomId()).orElse(null);
        User user = userRepository.findById(slot.getUserId()).orElse(null);

        return SlotResponse.builder()
                .id(slot.getId())
                .roomId(slot.getRoomId())
                .roomName(room != null ? room.getName() : "Salle supprimée")
                .userId(slot.getUserId())
                .userFullName(user != null ? user.getFirstName() + " " + user.getLastName() : "Utilisateur supprimé")
                .startTime(slot.getStartTime())
                .endTime(slot.getEndTime())
                .build();
    }
}
