package ea.sample.assignment.notification;

import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ObservablesTest {

    private final Message TEST_MESSAGE = new Message( 1, "ad", 1, 222 );
    private final User TEST_USER = new User( 1, "joe", "email", 1 );

    private Observables observables;

    ObserverUser testObserver = new ObserverUser( TEST_USER );

    @Before
    public void setUp() {
        observables = new Observables();
    }


    @Test
    public void givenTopicIsNeverSubscribedToBeforeThenItIsAddedToCollectionOfObservables() {

        assertThat( observables.size() ).isEqualTo( 0 );


        observables.add( TEST_USER, "gaming" );

        assertThat( observables.size() ).isEqualTo( 1 );
    }

    @Test
    public void givenTopicThenItIsAddedToCollectionOfObservables() {

        assertThat( observables.size() ).isEqualTo( 0 );

        observables.add( TEST_USER, "gaming" );
        observables.add( TEST_USER, "gaming" );

        assertThat( observables.size() ).isEqualTo( 1 );
    }


    @Test
    public void givenNoneSubscribedToTopicThenOnMessageAddedHasNoEffect() {
        observables.onMessageAdded( "new topic", TEST_MESSAGE );
    }

    @Test
    public void givenTopicWithSubsThenOnMessageAddedNotifiesAll() {
        String gamingTopic = "gaming";

        observables.add( TEST_USER, gamingTopic );

        observables.onMessageAdded( "gamingTopic", TEST_MESSAGE );
    }


}