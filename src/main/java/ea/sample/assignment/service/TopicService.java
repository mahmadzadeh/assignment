package ea.sample.assignment.service;

import ea.sample.assignment.dao.ITopicRepository;
import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.Topic;
import ea.sample.assignment.exeptions.TopicNotFoundException;
import ea.sample.assignment.util.IScoreQueue;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TopicService {

    private final ITopicRepository topicRepository;
    private final IScoreQueue scoreQueue;
    private final MessageService messageService;

    public TopicService( ITopicRepository topicRepository, IScoreQueue scoreQueue, MessageService messageService ) {
        this.topicRepository = topicRepository;
        this.scoreQueue = scoreQueue;
        this.messageService = messageService;
    }

    public Set<Topic> getTopics() {
        return topicRepository.readAll();
    }

    public Topic getTopic( String name ) {

        Optional<Topic> topic = topicRepository.read( name );

        return topic.orElseThrow( () -> new TopicNotFoundException( "Unable to find topic with name " + name ) );
    }

    public List<Message> getTopicMessages( String topicName, int count ) {
        return topicRepository
                .getLastNMessages( topicName, count )
                .stream()
                .map( id -> messageService.getMessage( id ) )
                .collect( Collectors.toList() );
    }

    public Message getTopicMessage( String topicName, long msgId ) {
        return messageService.getMessage( topicRepository.getTopicMessageWithId( topicName, msgId ) );
    }

    public Message createMessageForTopic( String topicName, Message msg ) {

        Message message = messageService.createMessage( msg.getMessage(), msg.getUserId() );

        topicRepository.createMessageForTopic( topicName, message );

        scoreQueue.enqueue( message );

        return message;
    }
}
