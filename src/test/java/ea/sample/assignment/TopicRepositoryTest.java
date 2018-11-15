package ea.sample.assignment;

import ea.sample.assignment.domain.Topic;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class TopicRepositoryTest
{
	private TopicRepository topicRepository;

	@Before
	public void setUp()
	{
		topicRepository= new TopicRepository();
	}


	@Test
	public void givenNotTopicsInDbThenGetTopicsReturnsEmptySet() throws Exception
	{
		assertThat( topicRepository.getTopics() ).isEqualTo( new HashSet<>(  ) );
	}

	@Test(expected = NullPointerException.class)
	public void shouldNotBeAbleToSaveInvalidTopic()
	{
		topicRepository.addTopic( "name", null );
	}

	@Test(expected = NullPointerException.class)
	public void shouldNotBeAbleToSaveInvalidTopicName()
	{
		topicRepository.addTopic( null,  new Topic( "somename" ));
	}

	@Test
	public void canAddNewValidTopic()
	{
		topicRepository.addTopic( "somename",  new Topic( "somename" ));

		assertThat( topicRepository.getTopics().size() ).isEqualTo( 1 );
	}

	@Test
	public void givenTopicsInDbThenGetTopicsReturnsThem() throws Exception
	{
		assertThat( topicRepository.getTopics() ).isEqualTo( new HashSet<>(  ) );
	}

	@Test
	public void getTopic() throws Exception
	{

	}

}