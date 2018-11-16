package ea.sample.assignment;

import com.ea.chat.score.interfaces.IChatScorer;
import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.Topic;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class TopicService {
    private final TopicRepository topicRepository;
    private final IChatScorer scorer;

    public TopicService( TopicRepository topicRepository, IChatScorer scorer ) {
        this.topicRepository = topicRepository;
        this.scorer = scorer;
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
        return topicRepository.getTopicMessageWithId( topicName, msgId );
    }

    public Message createMessageForTopic( String topicName, Message message ) {
        return topicRepository.createMessageForTopic( topicName, message.getMessage() );
    }
}
