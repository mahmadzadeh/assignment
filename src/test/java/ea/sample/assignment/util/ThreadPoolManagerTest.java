package ea.sample.assignment.util;

import com.ea.chat.score.interfaces.IChatScorer;
import ea.sample.assignment.domain.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

import static ea.sample.assignment.util.ThreadPoolManager.TIMEOUT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ThreadPoolManagerTest {
    @Mock
    private ExecutorService fakeExecuterService;

    @Mock
    private IChatScorer fakeScorer;

    @Mock
    private IScoreQueue fakeQueue;

    @Mock
    private Future<Optional<Integer>> fakeComputationResult;

    private ThreadPoolManager manager;


    @Before
    public void setUp() {
        manager = new ThreadPoolManager( fakeExecuterService, fakeScorer, fakeQueue );
    }


    @Test
    public void givenNoMessagesToScoreThenNothingToDo() {

        when( fakeQueue.dequeueItems( anyInt() ) ).thenReturn( Collections.EMPTY_LIST );

        manager.processMessagesToBeScored( 10 );

        verify( fakeExecuterService, times( 0 ) ).submit( any( Callable.class ) );
    }

    @Test
    public void givenMessagesToBeScoreWhenScoringTakesTooLongThenRelevantMessageRequeued()
            throws InterruptedException, ExecutionException, TimeoutException {

        List<Message> toBeScored = new ArrayList<Message>() {{
            add( new Message( 1, "", 0 ) );
        }};

        when( fakeQueue.dequeueItems( anyInt() ) ).thenReturn( toBeScored );
        when( fakeComputationResult.get( TIMEOUT, TimeUnit.SECONDS ) ).thenThrow( new TimeoutException() );
        when( fakeExecuterService.submit( any( Callable.class ) ) ).thenReturn( fakeComputationResult );

        manager.processMessagesToBeScored( 10 );

        verify( fakeExecuterService, times( 1 ) ).submit( any( Callable.class ) );
        verify( fakeQueue ).enqueue( any( Message.class ) );
    }

    @Test
    public void givenMessagesToBeScoreWhenScoringFailsThenRelevantMessageRequeued()
            throws InterruptedException, ExecutionException, TimeoutException {

        List<Message> toBeScored = new ArrayList<Message>() {{
            add( new Message( 1, "", 0 ) );
        }};

        when( fakeQueue.dequeueItems( anyInt() ) ).thenReturn( toBeScored );
        when( fakeComputationResult.get( TIMEOUT, TimeUnit.SECONDS ) ).thenReturn( Optional.empty() );
        when( fakeExecuterService.submit( any( Callable.class ) ) ).thenReturn( fakeComputationResult );

        manager.processMessagesToBeScored( 10 );

        verify( fakeExecuterService, times( 1 ) ).submit( any( Callable.class ) );
        verify( fakeQueue ).enqueue( any( Message.class ) );
    }


}