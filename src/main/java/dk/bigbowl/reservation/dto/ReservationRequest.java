package dk.bigbowl.reservation.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {
    private int numberOfParticipants;
    private LocalDate date;
    private String userName;
    private List<ReservationItemRequest> reservationItems;
}
