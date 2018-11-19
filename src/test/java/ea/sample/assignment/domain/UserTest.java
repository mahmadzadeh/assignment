package ea.sample.assignment.domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void testEquals() {
        // two users with the same email are equal
        User user = new User( 1, "name", "email", 1 );
        User user2 = new User( 2, "name", "email", 1 );

        assertEquals( user, user2 );
    }

    @Test
    public void testHashCode() {
        User user = new User( 1, "name", "email", 1 );
        User user2 = new User( 2, "name", "email", 1 );

        assertEquals( user.hashCode(), user2.hashCode() );
    }

    @Test
    public void usersAreSortedBasedOnRanking() {
        List<User> users = new ArrayList<>();

        users.add( new User( 1, "name", "email2", 666 ) );
        users.add( new User( 2, "name", "email3", 578 ) );
        users.add( new User( 2, "name", "email4", 1000 ) );
        users.add( new User( 2, "name", "email3", 1000 ) );
        users.add( new User( 2, "name", "email5", 1 ) );
        users.add( new User( 2, "name", "email5", 0 ) );

        Collections.sort( users );

        assertThat( users.get( 0 ).getRanking() ).isEqualTo( 1000 );
        assertThat( users.get( 1 ).getRanking() ).isEqualTo( 1000 );
        assertThat( users.get( 2 ).getRanking() ).isEqualTo( 666 );
        assertThat( users.get( 3 ).getRanking() ).isEqualTo( 578 );
        assertThat( users.get( 4 ).getRanking() ).isEqualTo( 1 );
        assertThat( users.get( 5 ).getRanking() ).isEqualTo( 0 );
    }

}