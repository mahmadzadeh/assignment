package ea.sample.assignment.dao;

import ea.sample.assignment.IdGenerator;
import ea.sample.assignment.MessageNotFoundException;
import ea.sample.assignment.TopicNotFoundException;
import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.Topic;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TopicRepository implements ITopicRepository {
    private final ConcurrentHashMap<String, Topic> inMemDb = new ConcurrentHashMap<>();

    @Override
    public Set<Topic> getTopics() {
        return new HashSet<>( inMemDb.values() );
    }

    @Override
    public Topic createTopic( String topicName ) {
        Topic topic = new Topic( IdGenerator.nextId(), topicName );

        inMemDb.put( topicName, topic );

        return topic;
    }

    @Override
    public Optional<Topic> getTopic( String name ) {
        return Optional.ofNullable( inMemDb.get( name ) );
    }

    @Override
    public void addTopic( String name, Topic topic ) {
        Objects.requireNonNull( name );
        Objects.requireNonNull( topic );

        inMemDb.put( name, topic );
    }

    @Override
    public List<Message> getLastNMessages( String topicName, int n ) {
        return getTopic( topicName )
                .map( topic -> topic.getLastN( n ) )
                .orElseThrow( () -> new TopicNotFoundException( "Invalid topic name " + topicName ) );
    }

    @Override
    public Message getTopicMessageWithId( String topicName, long msgId ) {
        Topic topic = getTopic( topicName ).orElseThrow( () -> new TopicNotFoundException( "Invalid topic name " + topicName ) );

        return topic.getMessages()
                .stream()
                .filter( msg -> msg.getId() == msgId )
                .findFirst()
                .orElseThrow( () -> new MessageNotFoundException( "Invalid msg id given" ) );
    }

    @Override
    public Message createMessageForTopic( String topicName, String messageContent ) {
        Optional<Topic> topic = getTopic( topicName );

        Message message = new Message( IdGenerator.nextId(), messageContent, null );

        if ( topic.isPresent() ) {
            topic.get().addMessage( message );
        } else {
            createTopic( topicName ).addMessage( message );
        }

        return message;
    }
}
