package dk.bigbowl.reservation.service;

import dk.bigbowl.reservation.dto.ReservationRequest;
import dk.bigbowl.reservation.dto.ReservationResponse;
import dk.bigbowl.reservation.entity.Reservation;

public interface ReservationService {
    ReservationResponse createReservation(ReservationRequest reservationRequest);
    ReservationResponse convertToDTO(Reservation reservation);
    Reservation convertToEntity(ReservationRequest reservationRequest);
}
