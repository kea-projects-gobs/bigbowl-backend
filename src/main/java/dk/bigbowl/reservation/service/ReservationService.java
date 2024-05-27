package dk.bigbowl.reservation.service;

import dk.bigbowl.reservation.dto.ReservationQuoteResDto;
import dk.bigbowl.reservation.dto.ReservationRequest;
import dk.bigbowl.reservation.dto.ReservationResponse;
import dk.bigbowl.reservation.entity.Reservation;

import java.security.Principal;
import java.util.List;

public interface ReservationService {
    ReservationResponse createReservation(ReservationQuoteResDto reservationRequest, Principal principal);
    List<ReservationResponse> getAllReservations(Principal principal);
}
