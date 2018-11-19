package ea.sample.assignment.service;

import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.Topic;
import ea.sample.assignment.dto.TopicDto;

import java.util.List;
import java.util.Set;

public interface ITopicService {
    Set<Topic> getTopics();

    Topic getTopic( String name );

    Topic createTopic( TopicDto topicDto );

    List<Message> getTopicMessages( String topicName, int count );

    Message getTopicMessage( String topicName, long msgId );

    Message createMessageForTopic( String topicName, Message msg );
}
