package ea.sample.assignment.dao;

import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.Topic;
import ea.sample.assignment.exeptions.DuplicateTopicException;
import ea.sample.assignment.exeptions.TopicNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class TopicRepositoryTest {
    private final String TEST_TOPIC_NAME = "somename";
    private final String TEST_TOPIC_NAME_2 = "someothername";


    private final Topic TEST_TOPIC_WITH_2_MSGS = new Topic( 1, TEST_TOPIC_NAME );
    private final Topic TEST_TOPIC_WITH_NO_MSG = new Topic( 1, TEST_TOPIC_NAME_2 );

    private ea.sample.assignment.dao.ITopicRepository topicRepository;

    @Before
    public void setUp() {
        topicRepository = new TopicRepository();

        TEST_TOPIC_WITH_2_MSGS.addMessage( 1 );
        TEST_TOPIC_WITH_2_MSGS.addMessage( 2 );
    }


    @Test
    public void givenNotTopicsInDbThenGetTopicsReturnsEmptySet() {
        assertThat( topicRepository.readAll() ).isEqualTo( new HashSet<>() );
    }

    @Test
    public void canAddNewValidTopic() {
        topicRepository.create( TEST_TOPIC_NAME );

        assertThat( topicRepository.readAll().size() ).isEqualTo( 1 );
    }

    @Test
    public void givenTopicsInDbThenGetTopicsReturnsThem() {
        assertThat( topicRepository.readAll() ).isEqualTo( new HashSet<>() );
    }

    @Test(expected = TopicNotFoundException.class)
    public void givenTopicNameNonExistentThenGetMessageThrowsRuntime() {

        topicRepository.create( TEST_TOPIC_NAME );

        assertThat( topicRepository.getLastNMessages( "bogus topic", 10 ) )
                .isEqualTo( Collections.EMPTY_LIST );
    }

    @Test
    public void givenValidTopicNameThenGetMessagesReturnsLastNMessagesForTopic() {

        topicRepository.create( TEST_TOPIC_NAME );
        topicRepository.create( TEST_TOPIC_NAME_2 );

        topicRepository.createMessageForTopic( TEST_TOPIC_NAME,
                new Message( 1, "hey", 0, 111 ) );
        topicRepository.createMessageForTopic( TEST_TOPIC_NAME,
                new Message( 2, "hey again", 0, 111 ) );

        assertThat( topicRepository.getLastNMessages( TEST_TOPIC_NAME, 10 ).size() )
                .isEqualTo( 2 );
    }

    @Test(expected = DuplicateTopicException.class)
    public void givenTopicAlreadyInSystemThenCreatingItAgainThrowsDuplicateException() {
        topicRepository.create( TEST_TOPIC_NAME );
        topicRepository.create( TEST_TOPIC_NAME );
    }

}