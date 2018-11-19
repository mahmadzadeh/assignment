package ea.sample.assignment.dao;

import ea.sample.assignment.domain.User;
import ea.sample.assignment.exeptions.DuplicateUserException;
import ea.sample.assignment.service.TopicService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryTest {


    @Mock
    private TopicService topicService;
    private IUserRepository userRepository;


    @Before
    public void setUp() {
        userRepository = new UserRepository();
    }

    @Test
    public void givenNoUsersInDbThenReadAllReturnsEmptySet() {
        assertThat( userRepository.readAll() ).isEqualTo( new HashSet<>() );
    }

    @Test(expected = DuplicateUserException.class)
    public void givenDuplicateUserThenRepositoryThrowsDuplicateUserException() {

        userRepository.create( "martin", "martin@domain.com" );

        userRepository.create( "martin-1", "martin@domain.com" );
    }


    @Test
    public void givenNonExistentUserThenReadReturnsOptionalEmpty() {
        assertThat( userRepository.read( 22 ) ).isEqualTo( Optional.empty() );
    }

    @Test
    public void givenUserThenSubscribedTopicsReturnsSetOfAllTopicsSubedByUser() {

        User martin = userRepository.create( "martin", "martin@domain.com" );

        martin.subscribe( "sports" );
        martin.subscribe( "comedy" );
        martin.subscribe( "movies" );


        Set<String> topics = userRepository.readTopics( martin.getId() );

        assertThat( topics.size() ).isEqualTo( 3 );

        assertThat( topics.contains( "sports" ) ).isTrue();
        assertThat( topics.contains( "comedy" ) ).isTrue();
    }

    @Test
    public void givenUserRankingsWhenLessThan_N_UsersThenReadTopRankedReturnsAllUsersInDescOrder() {

        User martin = userRepository.create( "martin", "martin@domain.com" );
        martin.addToRanking( 10 );

        User joe = userRepository.create( "joe", "joe@domain.com" );
        joe.addToRanking( 20 );

        List<User> users = userRepository.readTopRanked( 10 );

        assertThat( users.size() ).isEqualTo( 2 );

        assertThat( users.get( 0 ) ).isEqualTo( joe );
        assertThat( users.get( 1 ) ).isEqualTo( martin );
    }

    @Test
    public void givenMultipleUsersWithMultipleRankingsThenReadTopRankedReturnsTopN() {

        int userCount = 200;

        getTestUsersWithIncreasingRanking( userCount );

        // rankings will be from 0-199. Top ranked will have ranking from 199 down

        List<User> users = userRepository.readTopRanked( 10 );

        assertThat( users.size() ).isEqualTo( 10 );

        assertThat( users.get( 0 ).getRanking() ).isEqualTo( 199 );
        assertThat( users.get( 9 ).getRanking() ).isEqualTo( 190 );
    }

    private void getTestUsersWithIncreasingRanking( int count ) {
        for ( int i = 0; i < count; i++ ) {
            User user = userRepository.create( UUID.randomUUID().toString(), UUID.randomUUID().toString() );
            user.addToRanking( i );
        }
    }
}