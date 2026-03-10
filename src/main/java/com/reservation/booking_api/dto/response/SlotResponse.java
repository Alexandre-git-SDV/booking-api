package com.reservation.booking_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SlotResponse {

    private String id;
    private String roomId;
    private String roomName;
    private String userId;
    private String userFullName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
