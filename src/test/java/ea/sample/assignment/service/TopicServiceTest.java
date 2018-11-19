package ea.sample.assignment.service;

import ea.sample.assignment.dao.ITopicRepository;
import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.Topic;
import ea.sample.assignment.dto.TopicDto;
import ea.sample.assignment.exeptions.InvalidTopicException;
import ea.sample.assignment.exeptions.TopicNotFoundException;
import ea.sample.assignment.notification.Observables;
import ea.sample.assignment.util.ScoreQueue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
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
    private ITopicRepository mockTopicRepository;

    @Mock
    private ScoreQueue mockMessageScoreQueue;

    @Mock
    private MessageService mockMessageService;

    @Mock
    Observables mockObservableCollection;

    private Message testMessage = new Message( MESSAGE_ID, MESSAGE_TXT, 0, USER_ID );

    private ITopicService topicService;


    @Before
    public void setUp() {
        topicService = new TopicService( mockTopicRepository, mockMessageScoreQueue, mockMessageService, mockObservableCollection );
    }

    @Test
    public void givenRepositoryOfTopicsThenGetTopicsReturnAllOfThem() {
        when( mockTopicRepository.readAll() ).thenReturn( new HashSet<>() );

        Set<Topic> topics = topicService.getTopics();

        assertThat( topics.size() ).isEqualTo( 0 );
    }

    @Test(expected = TopicNotFoundException.class)
    public void givenInvalidTopicNameThenThrowTopicNotFound() {
        when( mockTopicRepository.read( anyString() ) ).thenReturn( Optional.empty() );

        topicService.getTopic( "something that is not in DB" );
    }

    @Test(expected = InvalidTopicException.class)
    public void givenNullTopicNameThenThrowTopicNotFound() {
        topicService.getTopic( "" );
    }

    @Test
    public void givenValidTopicNameThenReturnIt() {
        String sports = "sports";

        when( mockTopicRepository.read( anyString() ) ).thenReturn( Optional.of( new Topic( 1, sports ) ) );

        Topic topic = topicService.getTopic( sports );

        assertThat( topic.getName() ).isEqualTo( sports );
    }

    @Test
    public void givenTopicNameDoesNotExistAndValidMsgThenCreateTopicAndAddMessage() {

        doNothing().when( mockObservableCollection ).onMessageAdded( anyString(), any( Message.class ) );

        when( mockMessageService.createMessage( anyString(), anyLong() ) ).thenReturn( testMessage );

        when( mockTopicRepository.createMessageForTopic( anyString(), any( Message.class ) ) )
                .thenReturn( new Message( 1, "msg", 0, 0 ) );

        Message newMsg = topicService.createMessageForTopic( "topic", new Message( 1, "msg", 0, 0 ) );

        assertThat( newMsg.getId() ).isEqualTo( MESSAGE_ID );
        assertThat( newMsg.getMessage() ).isEqualTo( MESSAGE_TXT );
    }

    @Test
    public void newlyCreatedMsgIsQueuedToBeScored() {

        doNothing().when( mockObservableCollection ).onMessageAdded( anyString(), any( Message.class ) );

        when( mockMessageService.createMessage( anyString(), anyLong() ) ).thenReturn( testMessage );

        when( mockTopicRepository.createMessageForTopic( anyString(), any( Message.class ) ) )
                .thenReturn( new Message( 1, "msg", 0, 0 ) );

        doNothing().when( mockMessageScoreQueue ).enqueue( any( Message.class ) );

        Message newMsg = topicService.createMessageForTopic( "topic", new Message( 1, "msg", 0, 0 ) );

        assertThat( newMsg.getId() ).isEqualTo( MESSAGE_ID );
        assertThat( newMsg.getMessage() ).isEqualTo( MESSAGE_TXT );

        verify( mockMessageScoreQueue ).enqueue( any( Message.class ) );
    }


    @Test(expected = InvalidTopicException.class)
    public void givenTopicWithInvalidNameThenWhenCreatingThrowException() {
        topicService.createTopic( new TopicDto( -1, "", Collections.emptyList() ) );
    }


    @Test
    public void givenValidTopicThenItCanBeCreaed() {
        String name = "movies";

        Topic newlyCreatedTopic = new Topic( 1, name );

        when( mockTopicRepository.create( name ) ).thenReturn( newlyCreatedTopic );

        Topic topic = topicService.createTopic( new TopicDto( -1, name, Collections.emptyList() ) );


        assertThat( newlyCreatedTopic ).isEqualTo( topic );
    }

}