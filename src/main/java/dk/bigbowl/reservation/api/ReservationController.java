package dk.bigbowl.reservation.api;

import dk.bigbowl.reservation.dto.ReservationQuoteResDto;
import dk.bigbowl.reservation.dto.ReservationRequest;
import dk.bigbowl.reservation.dto.ReservationResponse;
import dk.bigbowl.reservation.service.ReservationService;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationQuoteResDto reservationRequest, Principal principal) {
        return ResponseEntity.ok().body(reservationService.createReservation(reservationRequest, principal));
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getAllReservations(Principal principal) {
        return ResponseEntity.ok().body(reservationService.getAllReservations(principal));
    }

}
