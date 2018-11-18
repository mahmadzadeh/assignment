package ea.sample.assignment.dao;

import ea.sample.assignment.domain.User;
import ea.sample.assignment.exeptions.DuplicateUserException;
import ea.sample.assignment.service.TopicService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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

}