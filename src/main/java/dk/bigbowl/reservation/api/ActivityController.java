package dk.bigbowl.reservation.api;

import dk.bigbowl.reservation.dto.ActivityQuoteReqDto;
import dk.bigbowl.reservation.dto.ReservationQuoteResDto;
import dk.bigbowl.reservation.entity.Reservation;
import dk.bigbowl.reservation.service.ActivityService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {
    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping
    public List<ReservationQuoteResDto> getAvailableActivities(@RequestBody ActivityQuoteReqDto activityQuoteReqDto) {
        return activityService.getAvailableActivities(activityQuoteReqDto);
    }
}
