package ea.sample.assignment;

import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.Topic;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TopicServiceTest {

    @Mock
    private TopicRepository topicRepository;

    private TopicService topicService;

    @Before
    public void setUp() {
        topicService = new TopicService( topicRepository, null );
    }

    @Test
    public void givenRepositoryOfTopicsThenGetTopicsReturnAllOfThem() {
        when( topicRepository.getTopics() ).thenReturn( new HashSet<>() );

        Set<Topic> topics = topicService.getTopics();

        assertThat( topics.size() ).isEqualTo( 0 );
    }

    @Test(expected = TopicNotFoundException.class)
    public void givenInvalidTopicNameThenThrowTopicNotFound() {
        when( topicRepository.getTopic( anyString() ) ).thenReturn( Optional.empty() );

        topicService.getTopic( anyString() );
    }

    @Test
    public void givenValidTopicNameThenReturnIt() {
        when( topicRepository.getTopic( anyString() ) ).thenReturn( Optional.of( new Topic( 1, "sports" ) ) );

        Topic topic = topicService.getTopic( anyString() );

        assertThat( topic.getName() ).isEqualTo( "sports" );
    }

    @Test
    public void givenTopicNameDoesNotExistAndValidMsgThenCreateTopicAndAddMessage() {
        when( topicRepository.getTopic( anyString() ) ).thenReturn( Optional.empty() );

        when( topicRepository.createMessageForTopic( anyString(), anyString() ) )
                .thenReturn( new Message( 1, "name" ) );

    }

}