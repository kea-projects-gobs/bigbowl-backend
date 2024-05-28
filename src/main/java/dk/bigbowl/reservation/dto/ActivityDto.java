package dk.bigbowl.reservation.dto;

import dk.bigbowl.reservation.entity.ActivityType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDto {
    private Long id;
    private String activityType;
    private String name;
    private String location;
    private String description;
    private boolean isActive;
    private String imageUrl;
    private int maxParticipants;
    private boolean isChildFriendly;
    private double price;
}
