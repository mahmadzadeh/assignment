package ea.sample.assignment;

import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.Topic;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TopicRepository {
    private final ConcurrentHashMap<String, Topic> inMemDb = new ConcurrentHashMap<>();

    public Set<Topic> getTopics() {
        return new HashSet<>( inMemDb.values() );
    }

    public Topic createTopic( String topicName ) {
        Topic topic = new Topic( IdGenerator.nextId(), topicName );

        inMemDb.put( topicName, topic );

        return topic;
    }

    public Optional<Topic> getTopic( String name ) {
        return Optional.ofNullable( inMemDb.get( name ) );
    }

    public void addTopic( String name, Topic topic ) {
        Objects.requireNonNull( name );
        Objects.requireNonNull( topic );

        inMemDb.put( name, topic );
    }

    public List<Message> getLastNMessages( String topicName, int n ) {
        return getTopic( topicName )
                .map( topic -> topic.getLastN( n ) )
                .orElseThrow( () -> new TopicNotFoundException( "Invalid topic name " + topicName ) );
    }

    public Message getTopicMessageWithId( String topicName, long msgId ) {
        Topic topic = getTopic( topicName ).orElseThrow( () -> new TopicNotFoundException( "Invalid topic name " + topicName ) );

        return topic.getMessages()
                .stream()
                .filter( msg -> msg.getId() == msgId )
                .findFirst()
                .orElseThrow( () -> new MessageNotFoundException( "Invalid msg id given" ) );
    }

    public Message createMessageForTopic( String topicName, String messageContent ) {
        Optional<Topic> topic = getTopic( topicName );

        Message message = new Message( IdGenerator.nextId(), messageContent );

        if ( topic.isPresent() ) {
            topic.get().addMessage( message );
        } else {
            createTopic( topicName ).addMessage( message );
        }

        return message;
    }
}
