package ea.sample.assignment;

import ea.sample.assignment.dao.ITopicRepository;
import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.Topic;
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

    @Mock
    private ITopicRepository topicRepository;

    @Mock
    private ScoreQueue messageScoreQueue;


    private TopicService topicService;


    @Before
    public void setUp() {
        topicService = new TopicService( topicRepository, messageScoreQueue );
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
        when( topicRepository.createMessageForTopic( anyString(), anyString() ) )
                .thenReturn( new Message( 1, "msg", 0 ) );

        Message newMsg = topicService.createMessageForTopic( "topic", new Message( 1, "msg", 0 ) );

        assertThat( newMsg.getId() ).isEqualTo( 1 );
        assertThat( newMsg.getMessage() ).isEqualTo( "msg" );
    }

    @Test
    public void newlyCreatedMsgIsQueuedToBeScored() {
        when( topicRepository.createMessageForTopic( anyString(), anyString() ) )
                .thenReturn( new Message( 1, "msg", 0 ) );

        doNothing().when( messageScoreQueue ).enqueue( any( Message.class ) );

        Message newMsg = topicService.createMessageForTopic( "topic", new Message( 1, "msg", 0 ) );

        assertThat( newMsg.getId() ).isEqualTo( 1 );
        assertThat( newMsg.getMessage() ).isEqualTo( "msg" );

        verify( messageScoreQueue ).enqueue( any( Message.class ) );
    }

}