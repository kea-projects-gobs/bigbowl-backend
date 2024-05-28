package dk.bigbowl.reservation.service;

import dk.bigbowl.reservation.dto.ActivityDto;
import dk.bigbowl.reservation.dto.ActivityQuoteReqDto;
import dk.bigbowl.reservation.dto.ReservationQuoteResDto;
import dk.bigbowl.reservation.entity.Reservation;

import java.security.Principal;
import java.util.List;

public interface ActivityService {
    List<ReservationQuoteResDto> getAvailableActivities(ActivityQuoteReqDto activityQuoteReqDto);
    List<ActivityDto> getAllActivities();
    ActivityDto toggleActivityStatus(Long id, boolean status, Principal principal);
}
