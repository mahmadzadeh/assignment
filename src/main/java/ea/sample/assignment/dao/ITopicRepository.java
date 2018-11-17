    package ea.sample.assignment.dao;

import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.Topic;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ITopicRepository {

    Topic createTopic( String topicName );

    Set<Topic> getTopics();

    Optional<Topic> getTopic( String name );

    void addTopic( String name, Topic topic );

    List<Message> getLastNMessages( String topicName, int n );

    Message getTopicMessageWithId( String topicName, long msgId );

    Message createMessageForTopic( String topicName, String messageContent );
}
