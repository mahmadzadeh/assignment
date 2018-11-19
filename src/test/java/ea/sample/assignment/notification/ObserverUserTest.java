package ea.sample.assignment.notification;

import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.User;
import org.junit.Test;

public class ObserverUserTest {

    @Test
    public void givenObserverThenOnUpdateNotifiesUser() {

        new ObserverUser( new User( 2, "martin", "email", 0 ) )
                .update( new Message( 1, "hi there new message here", 0, 2222 ), "sports" );
    }
}