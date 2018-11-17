package ea.sample.assignment.dao;

import ea.sample.assignment.IUserRepository;
import ea.sample.assignment.exeptions.UserNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

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
    public void readAll() {

    }

    @Test
    public void read() {

    }
}