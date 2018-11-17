package ea.sample.assignment.util;

import com.ea.chat.score.exceptions.ServiceUnavailableException;
import com.ea.chat.score.interfaces.IChatScorer;
import ea.sample.assignment.domain.Message;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ScoreMessageTaskTest {

    @Mock
    private IChatScorer mockScorer;
    private Message fakeMsg = new Message( 1, "asda", 0 );

    private ScoreMessageTask task;

    @Before
    public void setUp() {
        task = new ScoreMessageTask( mockScorer, fakeMsg );
    }

    @Test
    public void givenErrorWhenGettingScoreThenTaskReturnsOptionalEmpty() throws ServiceUnavailableException {
        when( mockScorer.score( anyString() ) ).thenThrow( new ServiceUnavailableException() );

        assertThat( task.call() ).isEqualTo( Optional.empty() );

    }

    @Test
    public void successfulScoringReturnsTheScore() throws ServiceUnavailableException {
        when( mockScorer.score( anyString() ) ).thenReturn( 42 );

        assertThat( task.call() ).isEqualTo( Optional.of( 42 ) );
    }

}