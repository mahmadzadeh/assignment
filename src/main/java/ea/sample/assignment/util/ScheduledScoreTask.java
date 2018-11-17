package ea.sample.assignment.util;

public class ScheduledScoreTask implements Runnable {

    private final int batchSize;
    private final ThreadPoolManager poolManager;

    public ScheduledScoreTask( ThreadPoolManager poolManager, int batchSize ) {
        this.poolManager = poolManager;
        this.batchSize = batchSize;
    }

    @Override
    public void run() {
        this.poolManager.processMessagesToBeScored( batchSize );
    }
}
