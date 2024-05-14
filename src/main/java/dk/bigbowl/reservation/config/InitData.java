package dk.bigbowl.reservation.config;

import dk.bigbowl.reservation.entity.Activity;
import dk.bigbowl.reservation.entity.ActivityType;
import dk.bigbowl.reservation.repository.ActivityRepository;
import dk.bigbowl.reservation.repository.ActivityTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class InitData implements CommandLineRunner {

    private final ActivityTypeRepository activityTypeRepository;
    private final ActivityRepository activityRepository;

    public InitData(ActivityTypeRepository activityTypeRepository, ActivityRepository activityRepository) {
        this.activityTypeRepository = activityTypeRepository;
        this.activityRepository = activityRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        createActivities();
    }


    public void createActivities() {
        ActivityType bowling = new ActivityType("Bowling", 100);
        ActivityType airHockey = new ActivityType("Air Hockey", 50);
        ActivityType dining = new ActivityType("Dining", 0);

        if (activityTypeRepository.findAll().isEmpty()) {
            // Save the activity types to the database
            activityTypeRepository.saveAll(List.of(bowling, airHockey, dining));
        }

        if (activityRepository.findAll().isEmpty()) {
            // Create 24 bowling lanes
            for (int i = 1; i <= 20; i++) {
                activityRepository.save(new Activity("Bowling Lane " + i, "Bowling lane" + i, "Lane " + i, bowling, true, null, 6, false));
            }
            for (int i = 21; i <= 24; i++) {
                activityRepository.save(new Activity("Bowling Lane " + i, "Bowling lane" + i, "Lane " + i, bowling, true, null, 6, true));
            }

            // Create 6 airhockey tables
            for (int i = 1; i <= 6; i++) {
                activityRepository.save(new Activity("Air Hockey Table " + i, "Air Hockey Table" + i, "Table " + i, airHockey, true, null, 2, false));
            }

            // Create 10 dining tables
            for (int i = 1; i <= 5; i++) {
                activityRepository.save(new Activity("Dining Table " + i, "Dining Table" + i, "Table " + i, dining, true, null, 4, false));
            }

            for (int i = 6; i <= 8; i++) {
                activityRepository.save(new Activity("Dining Table " + i, "Dining Table" + i, "Table " + i, dining, true, null, 6, false));
            }

            for (int i = 9; i <= 10; i++) {
                activityRepository.save(new Activity("Dining Table " + i, "Dining Table" + i, "Table " + i, dining, true, null, 2, false));
            }
        }
    }
}
