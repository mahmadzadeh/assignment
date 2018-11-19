package ea.sample.assignment.service;

import ea.sample.assignment.dao.IUserRepository;
import ea.sample.assignment.domain.Topic;
import ea.sample.assignment.domain.User;
import ea.sample.assignment.dto.UserDto;
import ea.sample.assignment.exeptions.DuplicateSubscriptionException;
import ea.sample.assignment.exeptions.InvalidUserException;
import ea.sample.assignment.exeptions.UserNotFoundException;
import ea.sample.assignment.notification.Observables;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private User testUser;

    @Mock
    private IUserRepository mockUserRepo;

    @Mock
    private MessageService mockMessageService;

    @Mock
    private Observables mockObservableCollection;

    private UserService userService;


    @Before
    public void setUp() {
        userService = new UserService( mockUserRepo, mockMessageService, mockObservableCollection );
        testUser = new User( 1, "joe", "glow", 0 );
    }

    @Test
    public void getUsers() {
        when( mockUserRepo.readAll() ).thenReturn( Collections.EMPTY_SET );

        assertThat( userService.getUsers() ).isEqualTo( Collections.EMPTY_SET );
    }

    @Test(expected = UserNotFoundException.class)
    public void givenInvalidTopicNameThenCreateSubscriptionThrowsRuntime() {

        when( mockUserRepo.read( anyLong() ) ).thenThrow( new UserNotFoundException( "" ) );

        userService.createSubscriptionFor( 1111, new Topic( 11, "" ) );
    }

    @Test(expected = DuplicateSubscriptionException.class)
    public void givenUserAlreadySubscribedToTopicThenCreateSubscriptionForSameTopicThrowsRuntime() {

        testUser.subscribe( "sports" );

        when( mockUserRepo.read( anyLong() ) ).thenReturn( Optional.of( testUser ) );

        userService.createSubscriptionFor( 1, new Topic( 11, "sports" ) );
    }

    @Test
    public void givenSubscriptionCreatedUserThenAddItIsAddedToObservableCollection() {

        when( mockUserRepo.read( anyLong() ) ).thenReturn( Optional.of( testUser ) );

        userService.createSubscriptionFor( 1111, new Topic( 11, "" ) );

        verify( mockObservableCollection ).add( any( User.class ), anyString() );

    }

    @Test(expected = InvalidUserException.class)
    public void givenInvalidUserWithBadUserNameThenThrowRuntime() {
        String invalidUsername = "";

        userService.createUser( new UserDto( 1, invalidUsername, "email@email.com", 0,
                Collections.EMPTY_SET ) );
    }

    @Test
    public void givenValidUserWithThenItCanBeCreated() {

        User newlyCreatedUser = new User( 1, "martin", "email@email.com", 0 );
        when( mockUserRepo.create( "martin", "email@email.com" ) ).thenReturn( newlyCreatedUser );

        User user = userService.createUser( new UserDto( 1, "martin", "email@email.com", 0,
                Collections.EMPTY_SET ) );

        assertThat( newlyCreatedUser ).isEqualTo( user );
    }

    @Test
    public void given() {

    }


}