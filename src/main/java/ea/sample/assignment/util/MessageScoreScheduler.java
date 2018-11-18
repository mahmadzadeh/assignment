package ea.sample.assignment.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class MessageScoreScheduler {

    private final ScheduledExecutorService scheduler;
    private final long pollingFreqInMIllis;

    public MessageScoreScheduler( int initialSize, long pollingFreqInMillis ) {
        scheduler = Executors.newScheduledThreadPool( initialSize );
        this.pollingFreqInMIllis = pollingFreqInMillis;
    }

    public MessageScoreScheduler( long pollingFreqInMIllis ) {
        this( 10, pollingFreqInMIllis );
    }

    public ScheduledFuture<?> submitTask( ScheduledScoreTask schduledScoreTask ) {
        ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate( schduledScoreTask, 0, pollingFreqInMIllis, MILLISECONDS );

        return scheduledFuture;
    }
}
