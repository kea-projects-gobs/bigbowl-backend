package dk.bigbowl.reservation.service.impl;

import dk.bigbowl.reservation.dto.ActivityDto;
import dk.bigbowl.reservation.dto.ActivityQuoteReqDto;
import dk.bigbowl.reservation.dto.ReservationItemQuoteResDto;
import dk.bigbowl.reservation.dto.ReservationQuoteResDto;
import dk.bigbowl.reservation.entity.Activity;
import dk.bigbowl.reservation.entity.Reservation;
import dk.bigbowl.reservation.entity.ReservationItem;
import dk.bigbowl.reservation.repository.ActivityRepository;
import dk.bigbowl.reservation.repository.ReservationItemRepository;
import dk.bigbowl.reservation.service.ActivityService;
import dk.security.repository.UserWithRolesRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ReservationItemRepository reservationItemRepository;
    private final ActivityRepository activityRepository;
    private final UserWithRolesRepository userWithRolesRepository;

    public ActivityServiceImpl(ReservationItemRepository reservationItemRepository, ActivityRepository activityRepository, UserWithRolesRepository userWithRolesRepository) {
        this.reservationItemRepository = reservationItemRepository;
        this.activityRepository = activityRepository;
        this.userWithRolesRepository = userWithRolesRepository;
    }

    // Current me is very happy, future me is very sad
    @Override
    public List<ReservationQuoteResDto> getAvailableActivities(ActivityQuoteReqDto activityQuoteReqDto) {

        // Only works for Bowling and Air Hockey
        int MAX_PARTICIPANTS = 0;

        String activityType = "";

        if (activityQuoteReqDto.getActivityType().contains("Bowling")) {
            MAX_PARTICIPANTS = 6;
            activityType = "Bowling";
        }
        if (activityQuoteReqDto.getActivityType().contains("Air Hockey")) {
            MAX_PARTICIPANTS = 2;
            activityType = "Air Hockey";
        }

        System.out.println(MAX_PARTICIPANTS);

        LocalTime startTime = LocalTime.parse(activityQuoteReqDto.getStartTime());
        LocalTime endTimeOneHour = startTime.plusHours(1);
        LocalTime endTimeTwoHours = startTime.plusHours(2);

        LocalDateTime startDateTime = LocalDateTime.of(activityQuoteReqDto.getDate(), startTime);
        LocalDateTime endDateTimeOneHour = LocalDateTime.of(activityQuoteReqDto.getDate(), endTimeOneHour);
        LocalDateTime endDateTimeTwoHours = LocalDateTime.of(activityQuoteReqDto.getDate(), endTimeTwoHours);

        List<Activity> availableActivitiesOneHour = getAvailableActivitiesGivenStartTimeAndEndTimeAndActivityType(
                startDateTime, endDateTimeOneHour, activityType
        );
        List<Activity> availableActivitiesTwoHours = getAvailableActivitiesGivenStartTimeAndEndTimeAndActivityType(
                startDateTime, endDateTimeTwoHours, activityType
        );


        int noOfActivitiesForAdults = (int) Math.ceil((double) activityQuoteReqDto.getNoOfAdults() / MAX_PARTICIPANTS);
        int noOfActivitiesForChildren = (int) Math.ceil((double) activityQuoteReqDto.getNoOfChildren() / MAX_PARTICIPANTS);

        List<Activity> availableActivitiesOneHourForAdults = availableActivitiesOneHour.stream().filter(activity -> !activity.isChildFriendly()).toList();
        List<Activity> availableActivitiesTwoHoursForAdults = availableActivitiesTwoHours.stream().filter(activity -> !activity.isChildFriendly()).toList();
        List<Activity> availableActivitiesOneHourForChildren = availableActivitiesOneHour.stream().filter(Activity::isChildFriendly).toList();
        List<Activity> availableActivitiesTwoHoursForChildren = availableActivitiesTwoHours.stream().filter(Activity::isChildFriendly).toList();

        if (noOfActivitiesForAdults > availableActivitiesOneHourForAdults.size()) System.out.println("Not enough activities for adults for 1 hour");
        if (noOfActivitiesForChildren > availableActivitiesOneHourForChildren.size()) System.out.println("Not enough activities for children for 1 hour");

        List<Activity> activitiesForAdultsOneHour = new ArrayList<>(availableActivitiesOneHourForAdults.subList(0, noOfActivitiesForAdults));
        List<Activity> activitiesForChildrenOneHour = new ArrayList<>(availableActivitiesOneHourForChildren.subList(0, noOfActivitiesForChildren));

        List<Activity> activitiesForOneHour = new ArrayList<>(activitiesForAdultsOneHour);
        activitiesForOneHour.addAll(activitiesForChildrenOneHour);


        if (noOfActivitiesForAdults > availableActivitiesTwoHoursForAdults.size()) System.out.println("Not enough activities for adults for 2 hours");
        if (noOfActivitiesForChildren > availableActivitiesTwoHoursForChildren.size()) System.out.println("Not enough activities for children for 2 hours");

        List<Activity> activitiesForAdultsTwoHours = new ArrayList<>(availableActivitiesTwoHoursForAdults.subList(0, noOfActivitiesForAdults));
        List<Activity> activitiesForChildrenTwoHours = new ArrayList<>(availableActivitiesTwoHoursForChildren.subList(0, noOfActivitiesForChildren));

        List<Activity> activitiesForTwoHours = new ArrayList<>(activitiesForAdultsTwoHours);
        activitiesForTwoHours.addAll(activitiesForChildrenTwoHours);

        if (activityQuoteReqDto.getActivityType().contains("Dining")) {
            Activity diningActivities = activityRepository.findAllByTypeName("Dining").stream().findFirst().orElse(null);
            activitiesForOneHour.add(diningActivities);
            activitiesForTwoHours.add(diningActivities);
        }

        Reservation reservationOneHour = buildReservationFromActivities(activityQuoteReqDto, activitiesForOneHour, startDateTime, endDateTimeOneHour);

        Reservation reservationTwoHours = buildReservationFromActivities(activityQuoteReqDto, activitiesForTwoHours, startDateTime, endDateTimeTwoHours);


        ReservationQuoteResDto reservationQuoteResDtoOneHour = fromReservationToReservationQuoteResDto(reservationOneHour);
        reservationQuoteResDtoOneHour.setNoOfAdults(activityQuoteReqDto.getNoOfAdults());
        reservationQuoteResDtoOneHour.setNoOfChildren(activityQuoteReqDto.getNoOfChildren());

        ReservationQuoteResDto reservationQuoteResDtoTwoHours = fromReservationToReservationQuoteResDto(reservationTwoHours);
        reservationQuoteResDtoTwoHours.setNoOfAdults(activityQuoteReqDto.getNoOfAdults());
        reservationQuoteResDtoTwoHours.setNoOfChildren(activityQuoteReqDto.getNoOfChildren());

        reservationQuoteResDtoOneHour.setActivityType(activityQuoteReqDto.getActivityType());
        reservationQuoteResDtoTwoHours.setActivityType(activityQuoteReqDto.getActivityType());

        return List.of(reservationQuoteResDtoOneHour, reservationQuoteResDtoTwoHours);
    }

    @Override
    public List<ActivityDto> getAllActivities() {
        return activityRepository.findAll().stream().map(this::fromActivityToActivityDto).collect(Collectors.toList());
    }

    @Override
    public ActivityDto toggleActivityStatus(Long id, boolean status, Principal principal) {
        String username = principal.getName();
        System.out.println("username: " + username);
        var user = userWithRolesRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        boolean isManager = user.getRoles().stream().anyMatch(role -> role.getRoleName().equals("MANAGER"));
        boolean isOperator = user.getRoles().stream().anyMatch(role -> role.getRoleName().equals("OPERATOR"));
        boolean isSalesMan = user.getRoles().stream().anyMatch(role -> role.getRoleName().equals("SALE"));

        if (!isManager && !isSalesMan && !isOperator){
            throw new RuntimeException("User not allowed");
        }

        Activity activity = activityRepository.findById(id).orElseThrow(() -> new RuntimeException("Activity not found"));
        activity.setActive(status);
        activityRepository.save(activity);

        return fromActivityToActivityDto(activity);
    }

    private ActivityDto fromActivityToActivityDto(Activity activity) {
        ActivityDto activityDto = new ActivityDto();
        activityDto.setId(activity.getId());
        activityDto.setActivityType(activity.getType().getName());
        activityDto.setName(activity.getName());
        activityDto.setLocation(activity.getLocation());
        activityDto.setDescription(activity.getDescription());
        activityDto.setActive(activity.isActive());
        activityDto.setImageUrl(activity.getImageUrl());
        activityDto.setMaxParticipants(activity.getMaxParticipants());
        activityDto.setChildFriendly(activity.isChildFriendly());
        activityDto.setPrice(activity.getType().getPrice());
        return activityDto;
    }

    private ReservationQuoteResDto fromReservationToReservationQuoteResDto(Reservation reservation) {
        ReservationQuoteResDto reservationQuoteResDto = new ReservationQuoteResDto();
        reservationQuoteResDto.setReservationItems(reservation.getItems().stream().map(reservationItem -> {
            ReservationItemQuoteResDto reservationItemQuoteResDto = new ReservationItemQuoteResDto();
            reservationItemQuoteResDto.setActivityId(reservationItem.getActivity().getId());

            // Calculate the price of reservation item
            double pricePerHour = reservationItem.getActivity().getType().getPrice();
            long duration = reservationItem.getStartTime().until(reservationItem.getEndTime(), java.time.temporal.ChronoUnit.HOURS);
            reservationItemQuoteResDto.setPrice(pricePerHour * duration);

            reservationItemQuoteResDto.setStartTime(reservationItem.getStartTime());
            reservationItemQuoteResDto.setEndTime(reservationItem.getEndTime());
            reservationItemQuoteResDto.setActivityName(reservationItem.getActivity().getType().getName());
            return reservationItemQuoteResDto;
        }).collect(Collectors.toList()));
        reservationQuoteResDto.setActivityType(reservation.getItems().get(0).getActivity().getType().getName());
        reservationQuoteResDto.setTotalPrice(reservation.getItems().stream().mapToDouble(reservationItem -> {
            double pricePerHour = reservationItem.getActivity().getType().getPrice();
            long duration = reservationItem.getStartTime().until(reservationItem.getEndTime(), java.time.temporal.ChronoUnit.HOURS);
            return pricePerHour * duration;
        }).sum());

        reservationQuoteResDto.setTotalPrice(reservationQuoteResDto.getReservationItems().stream().mapToDouble(ReservationItemQuoteResDto::getPrice).sum());
        reservationQuoteResDto.setTempReservationId(UUID.randomUUID());
        return reservationQuoteResDto;
    }

    private Reservation buildReservationFromActivities(ActivityQuoteReqDto activityQuoteReqDto, List<Activity> activities, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Reservation reservation = new Reservation();
        reservation.setDate(activityQuoteReqDto.getDate());
        reservation.setConfirmed(false);
        reservation.setNoOfParticipants(activityQuoteReqDto.getNoOfAdults() + activityQuoteReqDto.getNoOfChildren());
        reservation.setItems(activities.stream().map(activity -> {
            ReservationItem reservationItem = new ReservationItem();
            reservationItem.setActivity(activity);
            reservationItem.setPrice(activity.getType().getPrice());
            if (activity.getType().getName().contains("Dining")) {
                reservationItem.setStartTime(endDateTime);
                reservationItem.setEndTime(endDateTime.plusHours(2));
            } else {
                reservationItem.setStartTime(startDateTime);
                reservationItem.setEndTime(endDateTime);
            }
            reservationItem.setReservation(reservation);
            return reservationItem;
        }).collect(Collectors.toList()));
        return reservation;
    }

    private List<Activity> getAvailableActivitiesGivenStartTimeAndEndTimeAndActivityType( LocalDateTime startDateTime, LocalDateTime endDateTime, String activityType) {
        List<ReservationItem> overlappingReservations = reservationItemRepository.findOverlappingReservations(
                startDateTime,
                endDateTime,
                activityType
        );
        List<Activity> activities = activityRepository.findAllByTypeName(activityType);
        return activities.stream().filter(activity -> {
            for (ReservationItem reservationItem : overlappingReservations) {
                if (reservationItem.getActivity().getId().equals(activity.getId())) {
                    return false;
                }
            }
            return true;
        }).toList();
    }
}
