package dk.bigbowl.reservation.service.impl;

import dk.bigbowl.reservation.dto.ReservationItemRequest;
import dk.bigbowl.reservation.dto.ReservationItemResponse;
import dk.bigbowl.reservation.dto.ReservationRequest;
import dk.bigbowl.reservation.dto.ReservationResponse;
import dk.bigbowl.reservation.entity.Activity;
import dk.bigbowl.reservation.entity.Reservation;
import dk.bigbowl.reservation.entity.ReservationItem;
import dk.bigbowl.reservation.repository.ActivityRepository;
import dk.bigbowl.reservation.repository.ReservationItemRepository;
import dk.bigbowl.reservation.repository.ReservationRepository;
import dk.bigbowl.reservation.service.ReservationService;
import dk.security.repository.UserWithRolesRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserWithRolesRepository userWithRolesRepository;
    private final ActivityRepository activityRepository;
    private final ReservationItemRepository reservationItemRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, UserWithRolesRepository userWithRolesRepository, ActivityRepository activityRepository, ReservationItemRepository reservationItemRepository) {
        this.reservationRepository = reservationRepository;
        this.userWithRolesRepository = userWithRolesRepository;
        this.activityRepository = activityRepository;
        this.reservationItemRepository = reservationItemRepository;
    }


    @Override
    public ReservationResponse createReservation(ReservationRequest reservationRequest, Principal principal) {
        Reservation reservation = convertToEntity(reservationRequest, principal);
        reservationRepository.save(reservation);
        return convertToDTO(reservation);
    }

    @Override
    public ReservationResponse convertToDTO(Reservation reservation) {
        ReservationResponse reservationResponse = new ReservationResponse();
        reservationResponse.setId(reservation.getId());
        reservationResponse.setNoOfParticipants(reservation.getNoOfParticipants());
        reservationResponse.setUserName(reservation.getUser().getUsername());
        reservationResponse.setDate(reservation.getDate());

        List<ReservationItemResponse> reservationItemResponse = reservation.getItems().stream()
                .map(this::convertToReservationItemResponseDTO)
                .collect(Collectors.toList());
        reservationResponse.setReservationItems(reservationItemResponse);

        return reservationResponse;
    }



    @Override
    public Reservation convertToEntity(ReservationRequest reservationRequest, Principal principal) {
        Reservation reservation = new Reservation();
        reservation.setNoOfParticipants(reservationRequest.getNumberOfParticipants());
        reservation.setDate(reservationRequest.getDate());
        String username = principal.getName();
        reservation.setUser(userWithRolesRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found")));

        List<ReservationItem> reservationItems = reservationRequest.getReservationItems().stream()
                .map(item -> convertToReservationItemEntity(item, reservationRequest.getNumberOfParticipants()))
                .collect(Collectors.toList());

        reservationItems.forEach(reservationItem -> reservationItem.setReservation(reservation));
        reservation.setItems(reservationItems);

        return reservation;
    }


    // ReservationItem converters
    private ReservationItem convertToReservationItemEntity(ReservationItemRequest reservationItemRequest, int numberOfParticipants) {
        Activity activity = activityRepository.findByName(reservationItemRequest.getActivityName())
                .orElseThrow(() -> new RuntimeException("No available activity found"));

        // Check for overlapping reservations
        boolean isOverlapping = reservationItemRepository.findByActivityIdAndStartTimeLessThanAndEndTimeGreaterThan(
                activity.getId(), reservationItemRequest.getEndTime(), reservationItemRequest.getStartTime())
                .stream()
                .anyMatch(reservationItem -> true);

        if (isOverlapping) {
            throw new RuntimeException("The activity is already booked for the specified time slot");
        }

        ReservationItem reservationItem = new ReservationItem();
        reservationItem.setActivity(activity);
        // Calculate price based on number of participants
        reservationItem.setPrice(activity.getType().getPrice() * numberOfParticipants);
        reservationItem.setStartTime(reservationItemRequest.getStartTime());
        reservationItem.setEndTime(reservationItemRequest.getEndTime());

        return reservationItem;
    }

    private ReservationItemResponse convertToReservationItemResponseDTO(ReservationItem reservationItem) {
        ReservationItemResponse reservationItemResponse = new ReservationItemResponse();
        reservationItemResponse.setId(reservationItem.getId());
        reservationItemResponse.setActivityName(reservationItem.getActivity().getType().getName());
        reservationItemResponse.setPrice(reservationItem.getPrice());
        reservationItemResponse.setStartTime(reservationItem.getStartTime());
        reservationItemResponse.setEndTime(reservationItem.getEndTime());

        return reservationItemResponse;
    }
}
