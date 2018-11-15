package ea.sample.assignment;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.Topic;

public class TopicService
{
	private final TopicRepository topicRepository;

	public TopicService( TopicRepository topicRepository )
	{
		this.topicRepository = topicRepository;
	}

	public Set<Topic> getTopics()
	{
		return topicRepository.getTopics();
	}

	public Topic getTopic( String name )
	{
		Optional<Topic> topic = topicRepository.getTopic( name );

		return topic.orElseThrow( () -> new TopicNotFoundException( "Unable to find topic with name " + name ) );
	}

	public Collection<Message> getTopicMessages( String topicName, int count )
	{
		Collection<Message> messages = topicRepository.getMessages( topicName, count );

		return messages;
	}
}
