package ea.sample.assignment.dao;

import ea.sample.assignment.domain.Topic;
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

    private ea.sample.assignment.dao.ITopicRepository ITopicRepository;

    @Before
    public void setUp() {
        ITopicRepository = new TopicRepository();

        TEST_TOPIC_WITH_2_MSGS.addMessage( 1 );
        TEST_TOPIC_WITH_2_MSGS.addMessage( 2 );
    }


    @Test
    public void givenNotTopicsInDbThenGetTopicsReturnsEmptySet() {
        assertThat( ITopicRepository.readAll() ).isEqualTo( new HashSet<>() );
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotBeAbleToSaveInvalidTopic() {
        ITopicRepository.addTopic( "name", null );
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotBeAbleToSaveInvalidTopicName() {
        ITopicRepository.addTopic( null, TEST_TOPIC_WITH_2_MSGS );
    }

    @Test
    public void canAddNewValidTopic() {
        ITopicRepository.addTopic( TEST_TOPIC_NAME, TEST_TOPIC_WITH_2_MSGS );

        assertThat( ITopicRepository.readAll().size() ).isEqualTo( 1 );
    }

    @Test
    public void givenTopicsInDbThenGetTopicsReturnsThem() {
        assertThat( ITopicRepository.readAll() ).isEqualTo( new HashSet<>() );
    }

    @Test(expected = TopicNotFoundException.class)
    public void givenTopicNameNonExistentThenGetMessageThrowsRuntime() {

        ITopicRepository.addTopic( TEST_TOPIC_NAME, TEST_TOPIC_WITH_2_MSGS );

        assertThat( ITopicRepository.getLastNMessages( "bogus topic", 10 ) )
                .isEqualTo( Collections.EMPTY_LIST );
    }

    @Test
    public void givenValidTopicNameThenGetMessagesReturnsLastNMessagesForTopic() {

        ITopicRepository.addTopic( TEST_TOPIC_NAME, TEST_TOPIC_WITH_2_MSGS );
        ITopicRepository.addTopic( TEST_TOPIC_NAME_2, TEST_TOPIC_WITH_NO_MSG );

        assertThat( ITopicRepository.getLastNMessages( TEST_TOPIC_NAME, 10 ).size() )
                .isEqualTo( 2 );
    }

}