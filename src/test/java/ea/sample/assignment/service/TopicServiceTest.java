package ea.sample.assignment.service;

import ea.sample.assignment.dao.ITopicRepository;
import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.Topic;
import ea.sample.assignment.exeptions.TopicNotFoundException;
import ea.sample.assignment.util.ScoreQueue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TopicServiceTest {

    private final String MESSAGE_TXT = "message";
    private final int USER_ID = 1212;
    private final int MESSAGE_ID = 111;

    @Mock
    private ITopicRepository topicRepository;

    @Mock
    private ScoreQueue messageScoreQueue;

    @Mock
    private MessageService mockMessageService;

    private Message testMessage = new Message( MESSAGE_ID, MESSAGE_TXT, 0, USER_ID );

    private TopicService topicService;


    @Before
    public void setUp() {
        topicService = new TopicService( topicRepository, messageScoreQueue, mockMessageService );
    }

    @Test
    public void givenRepositoryOfTopicsThenGetTopicsReturnAllOfThem() {
        when( topicRepository.readAll() ).thenReturn( new HashSet<>() );

        Set<Topic> topics = topicService.getTopics();

        assertThat( topics.size() ).isEqualTo( 0 );
    }

    @Test(expected = TopicNotFoundException.class)
    public void givenInvalidTopicNameThenThrowTopicNotFound() {
        when( topicRepository.read( anyString() ) ).thenReturn( Optional.empty() );

        topicService.getTopic( anyString() );
    }

    @Test
    public void givenValidTopicNameThenReturnIt() {
        when( topicRepository.read( anyString() ) ).thenReturn( Optional.of( new Topic( 1, "sports" ) ) );

        Topic topic = topicService.getTopic( anyString() );

        assertThat( topic.getName() ).isEqualTo( "sports" );
    }

    @Test
    public void givenTopicNameDoesNotExistAndValidMsgThenCreateTopicAndAddMessage() {

        when( mockMessageService.createMessage( anyString(), anyLong() ) ).thenReturn( testMessage );

        when( topicRepository.createMessageForTopic( anyString(), any( Message.class ) ) )
                .thenReturn( new Message( 1, "msg", 0, 0 ) );

        Message newMsg = topicService.createMessageForTopic( "topic", new Message( 1, "msg", 0, 0 ) );

        assertThat( newMsg.getId() ).isEqualTo( MESSAGE_ID );
        assertThat( newMsg.getMessage() ).isEqualTo( MESSAGE_TXT );
    }

    @Test
    public void newlyCreatedMsgIsQueuedToBeScored() {
        when( mockMessageService.createMessage( anyString(), anyLong() ) ).thenReturn( testMessage );

        when( topicRepository.createMessageForTopic( anyString(), any( Message.class ) ) )
                .thenReturn( new Message( 1, "msg", 0, 0 ) );

        doNothing().when( messageScoreQueue ).enqueue( any( Message.class ) );

        Message newMsg = topicService.createMessageForTopic( "topic", new Message( 1, "msg", 0, 0 ) );

        assertThat( newMsg.getId() ).isEqualTo( MESSAGE_ID );
        assertThat( newMsg.getMessage() ).isEqualTo( MESSAGE_TXT );

        verify( messageScoreQueue ).enqueue( any( Message.class ) );
    }

}