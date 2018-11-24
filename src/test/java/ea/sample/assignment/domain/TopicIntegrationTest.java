package ea.sample.assignment.domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.assertj.core.api.Assertions.assertThat;

public class TopicIntegrationTest {

    @Test
    public void concurrentAccessToTopicDomainObject() throws InterruptedException {
        Topic topic = new Topic( 1, "very hot topic" );

        int countWriteThreads = 2000; // 2K concurrent writes into repository
        int countReadThreads = 2000; // 2K concurrent reads

        CountDownLatch startLatch = new CountDownLatch( 1 );
        CountDownLatch endLatch = new CountDownLatch( countReadThreads + countWriteThreads );

        List<Thread> messageCreationThreads = createNWriteThreads( startLatch, endLatch, countWriteThreads, topic );
        List<Thread> messageReadThreads = createNReadThreads( startLatch, endLatch, countWriteThreads, topic );

        startThreads( messageReadThreads );
        startThreads( messageCreationThreads );

        startLatch.countDown();

        endLatch.await();

        // ensure no lost writes
        assertThat( topic.getMessages().size() ).isEqualTo( countWriteThreads );
    }

    private void startThreads( List<Thread> threads ) {
        for ( Thread thread : threads )
            thread.start();
    }

    private List<Thread> createNReadThreads( CountDownLatch startLatch, CountDownLatch endLatch, int countOfReadThreads, Topic topic ) {
        List<Thread> threads = new ArrayList<>();

        for ( int i = 0; i < countOfReadThreads; i++ ) {
            threads.add( new Thread( () -> {
                try {
                    try {
                        startLatch.await();
                        topic.addMessage( (long) Math.floor( Math.random() * 20000000 ) );
                    } finally {
                        endLatch.countDown();
                    }

                } catch ( InterruptedException ignored ) {
                }

            } ) );
        }

        return threads;
    }

    private List<Thread> createNWriteThreads( CountDownLatch startLatch, CountDownLatch endLatch, int countWriteThreads, Topic topic ) {
        List<Thread> threads = new ArrayList<>();

        for ( int i = 0; i < countWriteThreads; i++ ) {
            threads.add( new Thread( () -> {
                try {
                    try {
                        startLatch.await();
                        topic.getLastN( topic.getMessages().size() );
                    } finally {
                        endLatch.countDown();
                    }

                } catch ( InterruptedException ignored ) {
                }

            } ) );
        }

        return threads;

    }

}