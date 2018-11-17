package ea.sample.assignment.util;

import com.ea.chat.score.interfaces.IChatScorer;
import ea.sample.assignment.domain.Message;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;

@Component
public class ThreadPoolManager {

    public static final int TIMEOUT = 2;
    private final ExecutorService executorService;
    private final IChatScorer scorer;
    private final IScoreQueue queue;

    public ThreadPoolManager( ExecutorService executorService, IChatScorer scorer, IScoreQueue queue ) {
        this.executorService = executorService;
        this.scorer = scorer;
        this.queue = queue;
    }


    public void processMessagesToBeScored( int batchSize ) {

        List<Message> messageBatch = queue.dequeueItems( batchSize );
        Map<Message, Future<Optional<Integer>>> futures = new HashMap<>();

        for ( Message message : messageBatch ) {
            Future<Optional<Integer>> future = executorService.submit( new ScoreMessageTask( scorer, message ) );
            futures.put( message, future );
        }

        for ( Message message : futures.keySet() ) {
            getResult( message, futures.get( message ) );
        }
    }

    private void getResult( Message message, Future<Optional<Integer>> optionalFuture ) {
        try {

            Optional<Integer> score = optionalFuture.get( TIMEOUT, TimeUnit.SECONDS );
            if ( score.isPresent() ) {
                message.setScore( score.get() );
            } else {
                // put it back in queue .. will try it again later!
                queue.enqueue( message );
            }
        } catch ( InterruptedException | ExecutionException | TimeoutException e ) {
            // rats need to requeue this message to be scored again, better luck next time
            queue.enqueue( message );
        }
    }
}
