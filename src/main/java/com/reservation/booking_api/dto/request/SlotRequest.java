package com.reservation.booking_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlotRequest {

    @NotBlank(message = "L'ID de la salle est obligatoire")
    private String roomId;

    @NotNull(message = "L'heure de début est obligatoire")
    private LocalDateTime startTime;

    @NotNull(message = "L'heure de fin est obligatoire")
    private LocalDateTime endTime;
}