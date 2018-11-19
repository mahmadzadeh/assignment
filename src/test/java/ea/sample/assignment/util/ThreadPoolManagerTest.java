package ea.sample.assignment.util;

import com.ea.chat.score.interfaces.IChatScorer;
import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.User;
import ea.sample.assignment.service.UserService;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ThreadPoolManagerTest {

    private final int ranking = 0;
    private final long userId = 1;

    private User testUser;

    @Mock
    private ExecutorService fakeExecuterService;

    @Mock
    private IChatScorer fakeScorer;

    @Mock
    private IScoreQueue fakeQueue;

    @Mock
    private Future<Optional<Integer>> fakeComputationResult;

    @Mock
    private UserService fakeUserService;


    private ThreadPoolManager manager;

    @Before
    public void setUp() {
        manager = new ThreadPoolManager( fakeExecuterService, fakeScorer, fakeQueue, fakeUserService );
        testUser = new User( userId, "martin", "martin@email.com", ranking );
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
            add( new Message( 1, "", 0, 0 ) );
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
            add( new Message( 1, "", 0, 0 ) );
        }};

        when( fakeQueue.dequeueItems( anyInt() ) ).thenReturn( toBeScored );
        when( fakeComputationResult.get( TIMEOUT, TimeUnit.SECONDS ) ).thenReturn( Optional.empty() );
        when( fakeExecuterService.submit( any( Callable.class ) ) ).thenReturn( fakeComputationResult );

        manager.processMessagesToBeScored( 10 );

        verify( fakeExecuterService, times( 1 ) ).submit( any( Callable.class ) );
        verify( fakeQueue ).enqueue( any( Message.class ) );
    }

    @Test
    public void givenMessagesScoredSuccessfullyThenRelevantUsersRankingIncreased()
            throws InterruptedException, ExecutionException, TimeoutException {

        List<Message> toBeScored = new ArrayList<Message>() {{
            add( new Message( 1, "", 0, 0 ) );
        }};

        when( fakeQueue.dequeueItems( anyInt() ) ).thenReturn( toBeScored );
        when( fakeComputationResult.get( TIMEOUT, TimeUnit.SECONDS ) ).thenReturn( Optional.of( 10000 ) );
        when( fakeExecuterService.submit( any( Callable.class ) ) ).thenReturn( fakeComputationResult );
        when( fakeUserService.getUser( anyLong() ) ).thenReturn( testUser );

        manager.processMessagesToBeScored( 10 );

        assertThat( toBeScored.get( 0 ).getScore() ).isEqualTo( 10000 );
        assertThat( testUser.getRanking() ).isEqualTo( 10000 );

        verify( fakeExecuterService, times( 1 ) ).submit( any( Callable.class ) );
        verify( fakeQueue, times( 0 ) ).enqueue( any( Message.class ) );
        verify( fakeUserService ).getUser( anyLong() );
    }


}