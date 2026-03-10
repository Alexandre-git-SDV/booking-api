package com.reservation.booking_api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "slots")
@Data
@Builder // Builder pour faciliter la création d'instances de Slot
@NoArgsConstructor // Constructeur sans arguments pour MongoDB
@AllArgsConstructor // Constructeur avec tous les arguments pour faciliter les tests et la création d'instances

public class Slot {

    @Id
    private String id;

    private String roomId;

    private String userId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}

