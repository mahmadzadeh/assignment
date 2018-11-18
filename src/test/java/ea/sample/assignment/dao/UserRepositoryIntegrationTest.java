package ea.sample.assignment.dao;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryIntegrationTest {

    private UserRepository userRepository;

    @Test
    public void loadAndFunctionalCorrectnessTestOfUserRepository() throws InterruptedException {
        userRepository = new UserRepository();

        int countWriteThreads = 2000; // 2K concurrent writes into repository
        int countReadThreads = 2000; // 2K concurrent reads

        CountDownLatch startLatch = new CountDownLatch( 1 );
        CountDownLatch endLatch = new CountDownLatch( countReadThreads + countWriteThreads );

        List<Thread> writeThreads = createNWriteThreads( startLatch, endLatch, countWriteThreads );
        List<Thread> readThreads = createNReadThreads( startLatch, endLatch, countWriteThreads );

        assertThat( writeThreads.size() ).isEqualTo( countWriteThreads );
        assertThat( readThreads.size() ).isEqualTo( countReadThreads );

        startThreads( readThreads );
        startThreads( writeThreads );

        startLatch.countDown();

        endLatch.await();

        assertThat( userRepository.size() ).isEqualTo( countWriteThreads );
    }

    @Ignore
    public void test() throws InterruptedException {
        while ( true ) {
            loadAndFunctionalCorrectnessTestOfUserRepository();
        }
    }

    private void startThreads( List<Thread> threads ) {
        for ( Thread thread : threads )
            thread.start();
    }

    private List<Thread> createNReadThreads( CountDownLatch startLatch, CountDownLatch endLatch, int countOfReadThreads ) {
        List<Thread> threads = new ArrayList<>();

        for ( int i = 0; i < countOfReadThreads; i++ ) {
            threads.add( new Thread( () -> {
                try {
                    try {
                        startLatch.await();
                        userRepository.readAll();
                    } finally {
                        endLatch.countDown();
                    }

                } catch ( InterruptedException ignored ) {
                }

            } ) );
        }

        return threads;
    }

    private List<Thread> createNWriteThreads( CountDownLatch startLatch, CountDownLatch endLatch, int countWriteThreads ) {
        List<Thread> threads = new ArrayList<>();

        for ( int i = 0; i < countWriteThreads; i++ ) {
            threads.add( new Thread( () -> {
                try {
                    try {
                        startLatch.await();
                        userRepository.create( UUID.randomUUID().toString(), "email@" + UUID.randomUUID().toString() );
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