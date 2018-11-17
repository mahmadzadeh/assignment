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
    private final ea.sample.assignment.dao.ITopicRepository topicRepository;
    private final IScoreQueue scoreQueue;

    public TopicService( ITopicRepository topicRepository, IScoreQueue scoreQueue ) {
        this.topicRepository = topicRepository;
        this.scoreQueue = scoreQueue;
    }

    public Set<Topic> getTopics() {
        return topicRepository.readAll();
    }

    public Topic getTopic( String name ) {
        Optional<Topic> topic = topicRepository.read( name );

        return topic.orElseThrow( () -> new TopicNotFoundException( "Unable to find topic with name " + name ) );
    }

    public List<Message> getTopicMessages( String topicName, int count ) {
        return topicRepository.getLastNMessages( topicName, count );
    }

    public Message getTopicMessage( String topicName, long msgId ) {
        return topicRepository.getTopicMessageWithId( topicName, msgId );
    }

    public Message createMessageForTopic( String topicName, Message msg ) {

        Message message = topicRepository.createMessageForTopic( topicName, msg.getMessage() );

        scoreQueue.enqueue( message );

        return message;
    }
}
