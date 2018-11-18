package ea.sample.assignment.service;

import ea.sample.assignment.dao.IUserRepository;
import ea.sample.assignment.domain.Topic;
import ea.sample.assignment.domain.User;
import ea.sample.assignment.dto.UserDto;
import ea.sample.assignment.exeptions.InvalidUserException;
import ea.sample.assignment.exeptions.UserNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private IUserRepository mockUserRepo;

    @Mock
    private MessageService mockMessageService;

    private UserService userService;


    @Before
    public void setUp() {
        userService = new UserService( mockUserRepo, mockMessageService );
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

}