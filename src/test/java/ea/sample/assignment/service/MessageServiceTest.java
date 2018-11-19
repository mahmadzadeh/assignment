package ea.sample.assignment.service;

import ea.sample.assignment.dao.IMessageRepository;
import ea.sample.assignment.domain.Message;
import ea.sample.assignment.exeptions.InvalidMessageException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MessageServiceTest {

    private final int USER_ID = 123;
    private final String MESSAGE = "message";

    @Mock
    private IMessageRepository mockRepo;

    private Message mockMessage = new Message( 1, MESSAGE, 0, USER_ID );

    @Test
    public void newlyCreatedMessagesAlwaysHaveScoreZero() {

        when( mockRepo.create( MESSAGE, USER_ID ) ).thenReturn( mockMessage );

        IMessageService service = new MessageService( mockRepo );

        assertThat( service.createMessage( MESSAGE, USER_ID ) ).isEqualTo( mockMessage );

    }

    @Test(expected = InvalidMessageException.class)
    public void invalidMessagesAreNotPersisted() {
        String invalidMsg = "";

        new MessageService( mockRepo ).createMessage( invalidMsg, 123 );

    }

}