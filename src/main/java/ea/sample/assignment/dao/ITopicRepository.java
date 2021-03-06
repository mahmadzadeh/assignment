package ea.sample.assignment.dao;

import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.Topic;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ITopicRepository {

    Topic create( String topicName );

    Set<Topic> readAll();

    Optional<Topic> read( String name );

    List<Long> getLastNMessages( String topicName, int n );

    Long getTopicMessageWithId( String topicName, long msgId );

    Message createMessageForTopic( String topicName, Message message );
}
