package ea.sample.assignment.util;

import com.ea.chat.score.interfaces.IChatScorer;
import ea.sample.assignment.domain.Message;
import ea.sample.assignment.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;

public class ThreadPoolManager {

    public static final int TIMEOUT = 2;
    private final ExecutorService executorService;
    private final IChatScorer scorer;
    private final IScoreQueue queue;
    private final UserService userService;

    public ThreadPoolManager( ExecutorService executorService,
                              IChatScorer scorer, IScoreQueue queue,
                              UserService userService ) {
        this.executorService = executorService;
        this.scorer = scorer;
        this.queue = queue;
        this.userService = userService;
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

            Optional<Integer> potentialScore = optionalFuture.get( TIMEOUT, TimeUnit.SECONDS );

            if ( potentialScore.isPresent() ) {
                updateMessageAndItsOwner( message, potentialScore );
            } else {
                // put it back in queue .. will try it again later!
                queue.enqueue( message );
            }
        } catch ( InterruptedException | ExecutionException | TimeoutException e ) {
            // rats need to requeue this message to be scored again, better luck next time
            queue.enqueue( message );
        }
    }

    private void updateMessageAndItsOwner( Message message, Optional<Integer> potentialScore ) {
        Integer score = potentialScore.get();

        message.setScore( score );

        userService.getUser( message.getUserId() ).addToRanking( score );
    }
}
