package ea.sample.assignment;

import com.ea.chat.score.ScorerService;
import ea.sample.assignment.util.MessageScoreScheduler;
import ea.sample.assignment.util.ScheduledScoreTask;
import ea.sample.assignment.util.ScoreQueue;
import ea.sample.assignment.util.ThreadPoolManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.concurrent.Executors;

@ComponentScan({ "com", "ea" })
@SpringBootApplication
public class AssignmentApplication {


    public static void main( String[] args ) {

        SpringApplication.run( AssignmentApplication.class, args );

        MessageScoreScheduler scoreScheduler = new MessageScoreScheduler( 1, 15000 );

        scoreScheduler.submitTask(
                new ScheduledScoreTask(
                        new ThreadPoolManager( Executors.newFixedThreadPool( 20 ),
                                new ScorerService().getScorer(),
                                new ScoreQueue()
                        ), 10 ) );
    }
}
