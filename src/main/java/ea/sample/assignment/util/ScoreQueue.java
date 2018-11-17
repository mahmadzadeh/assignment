package ea.sample.assignment.util;

import ea.sample.assignment.domain.Message;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class ScoreQueue implements IScoreQueue {

    private final Queue<Message> queue = new ConcurrentLinkedQueue<>();


    public List<Message> dequeueItems( int size ) {

        if ( queue.isEmpty() ) {
            return Collections.emptyList();
        }

        List<Message> toBeProcessed = new ArrayList<>();

        for ( int i = 1; i <= size; i++ ) {

            Message message = queue.poll();

            if ( isQueueEmpty( message ) ) {
                break;
            }

            toBeProcessed.add( message );
        }

        return toBeProcessed;
    }

    public void enqueue( Message message ) {
        this.queue.add( message );
    }

    public int size() {
        return this.queue.size();
    }

    private boolean isQueueEmpty( Message message ) {
        return message == null;
    }
}
