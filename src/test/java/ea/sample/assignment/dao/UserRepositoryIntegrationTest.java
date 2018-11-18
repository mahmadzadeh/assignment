package ea.sample.assignment.dao;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class UserRepositoryIntegrationTest {

    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository = new UserRepository();
    }


    @Test
    public void loadTestUserRepository() {

        int countReadThreads = 1000;
        int countWriteThreads = 1000;

        CountDownLatch startLatch = new CountDownLatch( 1 );
        CountDownLatch endLatch = new CountDownLatch( countReadThreads );

        //List<Thread>
    }


//    private Thread getTestThread( CountDownLatch startLatch, CountDownLatch endLatch, List<List<Message>> messagesSecondThread ) {
//        return new Thread( () -> {
//            try {
//                try {
//                    startLatch.await();
//                    messagesSecondThread.add( queue.dequeueItems( 10 ) );
//                } finally {
//                    endLatch.countDown();
//                }
//
//            } catch ( InterruptedException ignored ) {
//            }
//        } );
//    }

}