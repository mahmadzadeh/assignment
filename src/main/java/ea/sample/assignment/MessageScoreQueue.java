package ea.sample.assignment;

import ea.sample.assignment.domain.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageScoreQueue {

    private final Queue<Message> queue = new ConcurrentLinkedQueue<>();


    public List<Message> dequeueItems( int size ) {

        if ( queue.isEmpty() ) {
            return Collections.emptyList();
        }

        List<Message> toBeProcessed = new ArrayList<>();

        for ( int i = 1; i <= size && !queue.isEmpty(); i++ ) {
            Message message = queue.poll();

            if ( message == null ) {
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

}
