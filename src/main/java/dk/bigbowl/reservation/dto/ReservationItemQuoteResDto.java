package dk.bigbowl.reservation.dto;

import dk.bigbowl.reservation.entity.Activity;
import dk.bigbowl.reservation.entity.Reservation;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationItemQuoteResDto {
    private Long activityId;
    private String activityName;
    private double price;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
