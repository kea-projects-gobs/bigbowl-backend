package dk.bigbowl.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityQuoteReqDto {
    private LocalDate date;
    private String activityType;
    private int noOfAdults;
    private int noOfChildren;
    private String startTime;
}
