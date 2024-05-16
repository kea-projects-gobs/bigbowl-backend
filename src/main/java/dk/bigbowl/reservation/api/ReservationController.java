package dk.bigbowl.reservation.api;

import dk.bigbowl.reservation.dto.ReservationRequest;
import dk.bigbowl.reservation.dto.ReservationResponse;
import dk.bigbowl.reservation.service.ReservationService;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest reservationRequest, Principal principal) {
        return ResponseEntity.ok().body(reservationService.createReservation(reservationRequest, principal));
    }

}
