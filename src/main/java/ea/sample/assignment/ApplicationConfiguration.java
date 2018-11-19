package ea.sample.assignment;

import com.ea.chat.score.ScorerService;
import com.ea.chat.score.interfaces.IChatScorer;
import ea.sample.assignment.util.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.concurrent.Executors.newFixedThreadPool;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public IScoreQueue getScoreQueue() {
        return new ScoreQueue();
    }

    @Bean
    public MessageScoreScheduler scoreScheduler() {
        return new MessageScoreScheduler(
                scoreSchedulerInitialSize(),
                scorePollingFreqInMillies() );
    }

    @Bean
    public ScheduledScoreTask scheduledScoreTask() {
        return new ScheduledScoreTask( threadPoolManager(), scoreTaskBatchSize() );
    }

    @Bean
    public ThreadPoolManager threadPoolManager() {
        return
                new ThreadPoolManager(
                        newFixedThreadPool( scoringTaskThreadPoolSize() ),
                        scorer(),
                        getScoreQueue() );
    }

    @Bean
    public ScorerService scorerService() {
        return new ScorerService();
    }

    @Bean
    public IChatScorer scorer() {
        return scorerService().getScorer();
    }


    private long scorePollingFreqInMillies() {
        return 15000;
    }

    private int scoreSchedulerInitialSize() {
        return 1;
    }

    private int scoringTaskThreadPoolSize() {
        return 20;
    }

    private int scoreTaskBatchSize() {
        return 10;
    }

}
