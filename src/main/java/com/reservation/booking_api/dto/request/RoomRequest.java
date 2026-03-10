package com.reservation.booking_api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequest {

    @NotBlank(message = "Le nom de la salle est obligatoire")
    private String name;

    @Min(value = 1, message = "La capacité doit être d'au moins 1")
    private int capacity;

    private String description;
}

