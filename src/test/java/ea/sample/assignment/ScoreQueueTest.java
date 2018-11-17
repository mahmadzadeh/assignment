package ea.sample.assignment;

import ea.sample.assignment.domain.Message;
import ea.sample.assignment.util.ScoreQueue;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import static ea.sample.assignment.IdGenerator.nextId;
import static org.junit.Assert.assertEquals;

public class ScoreQueueTest {
    private ScoreQueue queue;

    @Before
    public void setUp() {
        queue = new ScoreQueue();
    }

    @Test
    public void concurrentProducerConsumer() throws InterruptedException {

        int totalMsgCount = 10;

        CountDownLatch startLatch = new CountDownLatch( 1 );
        CountDownLatch endLatch = new CountDownLatch( 3 );

        List<List<Message>> messagesFirstThread = new ArrayList<>();
        List<List<Message>> messagesSecondThread = new ArrayList<>();

        Thread consumerOne = getTestThread( startLatch, endLatch, messagesFirstThread );

        Thread consumerTwo = getTestThread( startLatch, endLatch, messagesSecondThread );

        Thread producer = new Thread( () -> {
            try {
                try {
                    startLatch.await();
                    addMessagesToQueue( queue, totalMsgCount );
                } finally {
                    endLatch.countDown();
                }

            } catch ( InterruptedException ignored ) {
            }
        } );


        consumerOne.start();
        consumerTwo.start();
        producer.start();

        startLatch.countDown();

        endLatch.await();

        int countOfConsumed = messagesSecondThread.get( 0 ).size() + messagesFirstThread.get( 0 ).size();
        int whatsLeftInQueue = queue.size();

        assertEquals( countOfConsumed + whatsLeftInQueue, totalMsgCount );
    }

    private Thread getTestThread( CountDownLatch startLatch, CountDownLatch endLatch, List<List<Message>> messagesSecondThread ) {
        return new Thread( () -> {
            try {
                try {
                    startLatch.await();
                    messagesSecondThread.add( queue.dequeueItems( 10 ) );
                } finally {
                    endLatch.countDown();
                }

            } catch ( InterruptedException ignored ) {
            }
        } );
    }

    private void addMessagesToQueue( ScoreQueue queue, int count ) {
        for ( int i = 0; i < count; i++ ) {
            queue.enqueue( new Message( nextId(), UUID.randomUUID().toString(), null ) );
        }
    }
}