package ea.sample.assignment.notification;

import ea.sample.assignment.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ObservableTopicCollectionTest {

    private final User TEST_USER = new User( 1, "joe", "email", 1 );

    @Mock
    ObserverBehaviour mockObserverBehaviour;

    private ObservableTopicCollection observableTopicCollection;

    ObserverUser testObserver = new ObserverUser( TEST_USER );

    @Before
    public void setUp() {
        observableTopicCollection = new ObservableTopicCollection();
    }


    @Test
    public void givenTopicIsNeverSubscribedToBeforeThenItIsAddedToCollectionOfObservables() {

        assertThat( observableTopicCollection.size() ).isEqualTo( 0 );


        observableTopicCollection.add( TEST_USER, "gaming" );

        assertThat( observableTopicCollection.size() ).isEqualTo( 1 );
    }

    @Test
    public void givenTopicThenItIsAddedToCollectionOfObservables() {

        assertThat( observableTopicCollection.size() ).isEqualTo( 0 );

        observableTopicCollection.add( TEST_USER, "gaming" );
        observableTopicCollection.add( TEST_USER, "gaming" );

        assertThat( observableTopicCollection.size() ).isEqualTo( 1 );
    }

}