package ea.sample.assignment;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.Topic;

public class TopicRepository
{
	private final ConcurrentHashMap<String, Topic> inMemDb = new ConcurrentHashMap<>();

	public Set<Topic> getTopics()
	{
		return new HashSet<>( inMemDb.values() );
	}

	public Optional<Topic> getTopic( String name )
	{
		return Optional.ofNullable( inMemDb.get( name ) );
	}

	public void addTopic( String name, Topic topic )
	{
		Objects.requireNonNull( name );
		Objects.requireNonNull( topic );

		inMemDb.put( name, topic );
	}

	public Collection<Message> getMessages( String topicName, int count )
	{
		return getTopic( topicName )
				.map( topic -> topic.getMessages() )
				.map( col -> getTopCount(col, count) )
				.orElse( Collections.EMPTY_LIST );
	}

	private  Collection<Message> getTopCount( Collection<Message> msgs , int count )
	{
		for ( int i = 0; i < count; i++ )
		{

		}
		return null;
	}
}
