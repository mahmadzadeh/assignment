package ea.sample.assignment.util;

import com.ea.chat.score.exceptions.ServiceUnavailableException;
import com.ea.chat.score.interfaces.IChatScorer;
import ea.sample.assignment.domain.Message;

import java.util.Optional;
import java.util.concurrent.Callable;

public class ScoreMessageTask implements Callable<Optional<Integer>> {

    private final IChatScorer scorer;
    private final Message message;

    public ScoreMessageTask( IChatScorer scorer, Message message ) {
        this.scorer = scorer;
        this.message = message;
    }

    @Override
    public Optional<Integer> call() {
        try {

            return Optional.of( scorer.score( message.getMessage() ) );

        } catch ( ServiceUnavailableException e ) {

            return Optional.empty();

        }
    }
}
