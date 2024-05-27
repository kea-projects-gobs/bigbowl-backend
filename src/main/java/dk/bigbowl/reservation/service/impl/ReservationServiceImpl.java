package dk.bigbowl.reservation.service.impl;

import dk.bigbowl.reservation.dto.ReservationItemResponse;
import dk.bigbowl.reservation.dto.ReservationQuoteResDto;
import dk.bigbowl.reservation.dto.ReservationResponse;
import dk.bigbowl.reservation.entity.Reservation;
import dk.bigbowl.reservation.entity.ReservationItem;
import dk.bigbowl.reservation.repository.ActivityRepository;
import dk.bigbowl.reservation.repository.ReservationRepository;
import dk.bigbowl.reservation.service.ReservationService;
import dk.security.repository.UserWithRolesRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final UserWithRolesRepository userWithRolesRepository;
    private final ActivityRepository activityRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, UserWithRolesRepository userWithRolesRepository, ActivityRepository activityRepository) {
        this.reservationRepository = reservationRepository;
        this.userWithRolesRepository = userWithRolesRepository;
        this.activityRepository = activityRepository;
    }

    @Override
    public ReservationResponse createReservation(ReservationQuoteResDto reservationRequest, Principal principal) {
        String username = principal.getName();
        System.out.println("username: " + username);
        Reservation reservation = new Reservation();
        reservation.setUser(userWithRolesRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found")));
        reservation.setNoOfParticipants(reservationRequest.getNoOfAdults()+reservationRequest.getNoOfChildren());
        reservation.setConfirmed(false);
        reservation.setDate(reservationRequest.getReservationItems().get(0).getStartTime().toLocalDate());
        reservation.setItems(reservationRequest.getReservationItems().stream()
                .map(reservationItem -> {
                    ReservationItem item = new ReservationItem();
                    item.setActivity(activityRepository.findById(reservationItem.getActivityId()).orElseThrow(() -> new RuntimeException("Activity not found")));
                    item.setStartTime(reservationItem.getStartTime());
                    item.setEndTime(reservationItem.getEndTime());
                    item.setReservation(reservation);

                    int noOfHours =  (reservationItem.getEndTime().getHour() - reservationItem.getStartTime().getHour());

                    item.setPrice(item.getActivity().getType().getPrice()*noOfHours);
                    return item;
                }).collect(Collectors.toList()));

        reservationRepository.save(reservation);

        return convertToDTO(reservation);
    }

    @Override
    public List<ReservationResponse> getAllReservations(LocalDate fromDate, LocalDate toDate,Principal principal) {

        System.out.println("fromDate: " + fromDate + " - toDate: " + toDate);

        String username = principal.getName();
        System.out.println("username: " + username);
        var user = userWithRolesRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        boolean isEmployee = user.getRoles().stream().anyMatch(role -> role.getRoleName().equals("EMPLOYEE"));
        boolean isAdmin = user.getRoles().stream().anyMatch(role -> role.getRoleName().equals("ADMIN"));
        boolean isCustomer = user.getRoles().stream().anyMatch(role -> role.getRoleName().equals("CUSTOMER"));

        List<Reservation> reservationList = new ArrayList<>();
        if (isCustomer) {
            reservationList = reservationRepository.findAllByUser(user);
        }
        if (isAdmin || isEmployee) {
            if (fromDate == null || toDate == null) {
                reservationList = reservationRepository.findAll();
            } else {
                reservationList = reservationRepository.findByDateRange(fromDate, toDate);
            }
        }

        if (!isCustomer && !isEmployee && !isAdmin) {
            throw new RuntimeException("User not allowed");
        }

        return reservationList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteReservation(Long id, Principal principal) {
        String username = principal.getName();
        System.out.println("username: " + username);
        var user = userWithRolesRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getRoles().stream().noneMatch(role -> role.getRoleName().equals("CUSTOMER"))) {
            throw new RuntimeException("User not allowed");
        }
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Reservation not found"));
        if (!reservation.getUser().equals(user)) {
            throw new RuntimeException("User not allowed");
        }
        reservationRepository.delete(reservation);
    }

    @Override
    public ReservationResponse confirmReservation(Long id, boolean status, Principal principal) {
        String username = principal.getName();
        System.out.println("username: " + username);
        var user = userWithRolesRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        boolean isEmployee = user.getRoles().stream().anyMatch(role -> role.getRoleName().equals("EMPLOYEE"));
        boolean isAdmin = user.getRoles().stream().anyMatch(role -> role.getRoleName().equals("ADMIN"));

        if (!isEmployee && !isAdmin) {
            throw new RuntimeException("User not allowed");
        }

        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Reservation not found"));
        reservation.setConfirmed(status);
        reservationRepository.save(reservation);

        return convertToDTO(reservation);
    }


    private ReservationResponse convertToDTO(Reservation reservation) {
        ReservationResponse reservationResponse = new ReservationResponse();
        reservationResponse.setId(reservation.getId());
        reservationResponse.setNoOfParticipants(reservation.getNoOfParticipants());
        reservationResponse.setUserName(reservation.getUser().getUsername());
        reservationResponse.setDate(reservation.getDate());
        reservationResponse.setConfirmed(reservation.isConfirmed());

        reservationResponse.setReservationItems(reservation.getItems().stream()
                .map(reservationItem -> {
                    ReservationItemResponse itemResponse = new ReservationItemResponse();
                    itemResponse.setId(reservationItem.getId());
                    itemResponse.setActivityName(reservationItem.getActivity().getName());
                    itemResponse.setPrice(reservationItem.getPrice());
                    itemResponse.setStartTime(reservationItem.getStartTime());
                    itemResponse.setEndTime(reservationItem.getEndTime());
                    return itemResponse;
                }).collect(Collectors.toList()));

        return reservationResponse;
    }

}
