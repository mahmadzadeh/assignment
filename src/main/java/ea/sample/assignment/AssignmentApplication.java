package ea.sample.assignment;

import ea.sample.assignment.util.MessageScoreScheduler;
import ea.sample.assignment.util.ScheduledScoreTask;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({ "com", "ea" })
@SpringBootApplication
public class AssignmentApplication {

    public static void main( String[] args ) {

        ConfigurableApplicationContext run = SpringApplication.run( AssignmentApplication.class, args );

        MessageScoreScheduler scoreScheduler = run.getBean( MessageScoreScheduler.class );

        ScheduledScoreTask scheduledScoreTask = run.getBean( ScheduledScoreTask.class );

        scoreScheduler.submitTask( scheduledScoreTask );
    }
}
