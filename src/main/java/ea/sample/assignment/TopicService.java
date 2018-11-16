package ea.sample.assignment;

import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.Topic;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class TopicService {
    private final TopicRepository topicRepository;

    public TopicService( TopicRepository topicRepository ) {
        this.topicRepository = topicRepository;
    }

    public Set<Topic> getTopics() {
        return topicRepository.getTopics();
    }

    public Topic getTopic( String name ) {
        Optional<Topic> topic = topicRepository.getTopic( name );

        return topic.orElseThrow( () -> new TopicNotFoundException( "Unable to find topic with name " + name ) );
    }

    public List<Message> getTopicMessages( String topicName, int count ) {
        return topicRepository.getLastNMessages( topicName, count );
    }

    public Message getTopicMessage( String topicName, long msgId ) {
        return topicRepository.getTopicMessageWithId( topicName, msgId);
    }

    public Message createMessageForTopic( String topicName, Message message ) {
        return null;
    }
}
