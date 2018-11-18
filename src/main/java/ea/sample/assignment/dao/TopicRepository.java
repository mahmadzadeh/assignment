package ea.sample.assignment.dao;

import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.Topic;
import ea.sample.assignment.exeptions.DuplicateTopicException;
import ea.sample.assignment.exeptions.MessageNotFoundException;
import ea.sample.assignment.exeptions.TopicNotFoundException;
import ea.sample.assignment.util.IdGenerator;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TopicRepository implements ITopicRepository {
    private final ConcurrentHashMap<String, Topic> inMemDb = new ConcurrentHashMap<>();

    @Override
    public Topic create( String topicName ) {

        if ( topicAlreadyExist( topicName ) ) {
            throw new DuplicateTopicException( "Topic with name " + topicName + " already in system" );
        }

        Topic topic = new Topic( IdGenerator.nextId(), topicName );

        inMemDb.put( topicName, topic );

        return topic;
    }

    @Override
    public Set<Topic> readAll() {
        return new HashSet<>( inMemDb.values() );
    }

    @Override
    public Optional<Topic> read( String name ) {
        return Optional.ofNullable( inMemDb.get( name ) );
    }

    @Override
    public List<Long> getLastNMessages( String topicName, int n ) {
        return read( topicName )
                .map( topic -> topic.getLastN( n ) )
                .orElseThrow( () -> new TopicNotFoundException( "Invalid topic name " + topicName ) );
    }

    @Override
    public Long getTopicMessageWithId( String topicName, long msgId ) {
        Topic topic = read( topicName ).orElseThrow( () -> new TopicNotFoundException( "Invalid topic name " + topicName ) );

        return topic.getMessages()
                .stream()
                .filter( msg -> msg.equals( msgId ) )
                .findFirst()
                .orElseThrow( () -> new MessageNotFoundException( "Invalid msg id given" ) );
    }

    @Override
    public Message createMessageForTopic( String topicName, Message message ) {
        Optional<Topic> topic = read( topicName );

        if ( topic.isPresent() ) {
            topic.get().addMessage( message.getId() );
        } else {
            create( topicName ).addMessage( message.getId() );
        }

        return message;
    }

    private boolean topicAlreadyExist( String topicName ) {
        return inMemDb.get( topicName ) != null;
    }
}
