package ea.sample.assignment;

import ea.sample.assignment.dao.ITopicRepository;
import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.Topic;
import ea.sample.assignment.util.IScoreQueue;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TopicService {
    private final ea.sample.assignment.dao.ITopicRepository ITopicRepository;
    private final IScoreQueue scoreQueue;

    public TopicService( ITopicRepository ITopicRepository, IScoreQueue scoreQueue ) {
        this.ITopicRepository = ITopicRepository;
        this.scoreQueue = scoreQueue;
    }

    public Set<Topic> getTopics() {
        return ITopicRepository.getTopics();
    }

    public Topic getTopic( String name ) {
        Optional<Topic> topic = ITopicRepository.getTopic( name );

        return topic.orElseThrow( () -> new TopicNotFoundException( "Unable to find topic with name " + name ) );
    }

    public List<Message> getTopicMessages( String topicName, int count ) {
        return ITopicRepository.getLastNMessages( topicName, count );
    }

    public Message getTopicMessage( String topicName, long msgId ) {
        return ITopicRepository.getTopicMessageWithId( topicName, msgId );
    }

    public Message createMessageForTopic( String topicName, Message message ) {

        Message messageForTopic = ITopicRepository.createMessageForTopic( topicName, message.getMessage() );

        scoreQueue.enqueue( message );

        return messageForTopic;
    }
}
