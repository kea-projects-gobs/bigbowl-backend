package dk.bigbowl.reservation.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ReservationResponse {
    private Long id;
    private int noOfParticipants;
    private LocalDate date;
    private String userName;
    private List<ReservationItemResponse> reservationItems;
    private boolean isConfirmed;

}
