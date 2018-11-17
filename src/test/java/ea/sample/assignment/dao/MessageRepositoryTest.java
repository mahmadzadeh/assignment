package ea.sample.assignment.dao;

import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.User;
import ea.sample.assignment.exeptions.UserNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MessageRepositoryTest {

    @Mock
    private IUserRepository mockUserRepo;

    private IMessageRepository messageRepository;


    @Before
    public void setUp() {
        messageRepository = new MessageRepository( mockUserRepo );
    }


    @Test(expected = UserNotFoundException.class)
    public void givenInvalidUserIdThenMessageWillNotBeCreated() {

        when( mockUserRepo.read( anyLong() ) ).thenReturn( Optional.empty() );

        messageRepository.create( "msg", 0, 123 );

    }

    @Test
    public void givenNoValueInDatabaseThenReadAllReturnsEmptySet() {
        assertThat( messageRepository.readAll() ).isEqualTo( Collections.emptySet() );
    }

    @Test
    public void givenNoMessageWithIdExistThenReadWillReturnsEmptyOption() {

        assertThat( messageRepository.read( 1 ) ).isEqualTo( Optional.empty() );

    }

    @Test
    public void givenMessageWithIdExistThenReadWillReturnsIt() {

        when( mockUserRepo.read( anyLong() ) ).thenReturn( Optional.of( new User( 123, "joe", "email" ) ) );

        Message message = messageRepository.create( "one", 0, 123 );

        assertThat( messageRepository.read( 1 ) ).isEqualTo( Optional.of( message ) );
    }

}