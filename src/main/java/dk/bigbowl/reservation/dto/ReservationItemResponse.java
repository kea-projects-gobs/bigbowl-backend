package dk.bigbowl.reservation.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationItemResponse {
    private Long id;
    private String activityName;
    private double price;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
