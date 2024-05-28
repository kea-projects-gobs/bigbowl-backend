package dk.bigbowl.reservation.api;

import dk.bigbowl.reservation.dto.ActivityDto;
import dk.bigbowl.reservation.dto.ActivityQuoteReqDto;
import dk.bigbowl.reservation.dto.ReservationQuoteResDto;
import dk.bigbowl.reservation.entity.Activity;
import dk.bigbowl.reservation.entity.Reservation;
import dk.bigbowl.reservation.service.ActivityService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

    @GetMapping
    public List<ActivityDto> getAllActivities() {
        return activityService.getAllActivities();
    }

    @PatchMapping("/{id}")
    public ActivityDto toggleActivityStatus(@PathVariable Long id,@RequestParam(name = "status") boolean status, Principal principal) {
        return activityService.toggleActivityStatus(id,status, principal);
    }
}

