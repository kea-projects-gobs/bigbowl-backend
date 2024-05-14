package dk.bigbowl.reservation.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String location;
    @ManyToOne
    private ActivityType type;
    private boolean isActive;
    private String imageUrl;
    private int maxParticipants;
    private boolean isChildFriendly;


    public Activity (String name, String description, String location, ActivityType type, boolean isActive, String imageUrl, int maxParticipants, boolean isChildFriendly) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.type = type;
        this.isActive = isActive;
        this.imageUrl = imageUrl;
        this.maxParticipants = maxParticipants;
        this.isChildFriendly = isChildFriendly;
    }

}
