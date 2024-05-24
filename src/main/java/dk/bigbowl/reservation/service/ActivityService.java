package dk.bigbowl.reservation.service;

import dk.bigbowl.reservation.dto.ActivityQuoteReqDto;
import dk.bigbowl.reservation.dto.ReservationQuoteResDto;
import dk.bigbowl.reservation.entity.Reservation;

import java.util.List;

public interface ActivityService {
    List<ReservationQuoteResDto> getAvailableActivities(ActivityQuoteReqDto activityQuoteReqDto);
}
