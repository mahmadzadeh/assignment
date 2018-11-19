package ea.sample.assignment.service;

import ea.sample.assignment.dao.ITopicRepository;
import ea.sample.assignment.domain.Message;
import ea.sample.assignment.domain.Topic;
import ea.sample.assignment.dto.TopicDto;
import ea.sample.assignment.exeptions.InvalidTopicException;
import ea.sample.assignment.exeptions.TopicNotFoundException;
import ea.sample.assignment.notification.Observables;
import ea.sample.assignment.util.IScoreQueue;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class TopicService implements ITopicService {

    private final ITopicRepository topicRepository;
    private final IScoreQueue scoreQueue;
    private final IMessageService messageService;
    private final Observables observableCollection;

    public TopicService( ITopicRepository topicRepository,
                         IScoreQueue scoreQueue,
                         IMessageService messageService,
                         Observables observableCollection ) {
        this.topicRepository = topicRepository;
        this.scoreQueue = scoreQueue;
        this.messageService = messageService;
        this.observableCollection = observableCollection;
    }

    @Override
    public Set<Topic> getTopics() {
        return topicRepository.readAll();
    }

    @Override
    public Topic getTopic( String name ) {
        if ( isBlank( name ) ) {
            throw new InvalidTopicException( "Topic name can not be empty. Given " + name );
        }

        Optional<Topic> topic = topicRepository.read( name );

        return topic.orElseThrow( () -> new TopicNotFoundException( "Unable to find topic with name " + name ) );
    }

    @Override
    public Topic createTopic( TopicDto topicDto ) {

        if ( isBlank( topicDto.getName() ) ) {
            throw new InvalidTopicException( "Invalid topic name given " + topicDto.getName() );
        }

        return topicRepository.create( topicDto.getName() );
    }

    @Override
    public List<Message> getTopicMessages( String topicName, int count ) {
        return topicRepository
                .getLastNMessages( topicName, count )
                .stream()
                .map( id -> messageService.getMessage( id ) )
                .collect( Collectors.toList() );
    }

    @Override
    public Message getTopicMessage( String topicName, long msgId ) {
        return messageService.getMessage( topicRepository.getTopicMessageWithId( topicName, msgId ) );
    }

    @Override
    public Message createMessageForTopic( String topicName, Message msg ) {

        Message message = messageService.createMessage( msg.getMessage(), msg.getUserId() );

        topicRepository.createMessageForTopic( topicName, message );

        scoreQueue.enqueue( message );

        observableCollection.onMessageAdded( topicName, message );

        return message;
    }
}
