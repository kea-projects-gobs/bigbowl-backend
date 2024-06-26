package dk.bigbowl.reservation.api;

import dk.bigbowl.reservation.dto.ReservationQuoteResDto;
import dk.bigbowl.reservation.dto.ReservationRequest;
import dk.bigbowl.reservation.dto.ReservationResponse;
import dk.bigbowl.reservation.service.ReservationService;

import java.security.Principal;
import java.time.LocalDate;
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
    public ResponseEntity<List<ReservationResponse>> getAllReservations(@RequestParam(name = "from",required = false) LocalDate fromDate, @RequestParam(name = "to",required = false) LocalDate toDate, Principal principal) {
        return ResponseEntity.ok().body(reservationService.getAllReservations(fromDate,toDate,principal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id, Principal principal) {
        reservationService.deleteReservation(id, principal);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ReservationResponse> confirmReservation(@PathVariable Long id, @RequestParam(name = "status") boolean status, Principal principal) {
        return ResponseEntity.ok().body(reservationService.confirmReservation(id,status, principal));
    }

}
