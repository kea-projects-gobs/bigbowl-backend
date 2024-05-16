package dk.bigbowl.reservation.service;

import dk.bigbowl.reservation.dto.ReservationRequest;
import dk.bigbowl.reservation.dto.ReservationResponse;
import dk.bigbowl.reservation.entity.Reservation;

import java.security.Principal;

public interface ReservationService {
    ReservationResponse createReservation(ReservationRequest reservationRequest, Principal principal);
    ReservationResponse convertToDTO(Reservation reservation);
    Reservation convertToEntity(ReservationRequest reservationRequest, Principal principal);
}
