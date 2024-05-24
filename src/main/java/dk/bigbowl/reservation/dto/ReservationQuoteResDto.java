package dk.bigbowl.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationQuoteResDto {
    private UUID tempReservationId;
    private String activityType;
    private int noOfAdults;
    private int noOfChildren;
    private List<ReservationItemQuoteResDto> reservationItems;
    private double totalPrice;
}
