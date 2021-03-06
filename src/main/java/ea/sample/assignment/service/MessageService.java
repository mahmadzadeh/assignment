package ea.sample.assignment.service;

import ea.sample.assignment.dao.IMessageRepository;
import ea.sample.assignment.domain.Message;
import ea.sample.assignment.exeptions.InvalidMessageException;
import ea.sample.assignment.exeptions.TopicNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class MessageService implements IMessageService {

    private final IMessageRepository messageRepository;

    public MessageService( IMessageRepository messageRepository ) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Set<Message> getMessages() {
        return messageRepository.readAll();
    }

    @Override
    public Message getMessage( long id ) {
        Optional<Message> message = messageRepository.read( id );

        return message.orElseThrow( () -> new TopicNotFoundException( "Unable to find topic with id " + id ) );
    }

    @Override
    public Set<Message> getUserMessages( long userId ) {
        return messageRepository.readMessagesForUser( userId );
    }

    @Override
    public Message createMessage( String msg, long userId ) {
        if ( isBlank( msg ) ) {
            throw new InvalidMessageException( "Message content can not be empty" );
        }

        return messageRepository.create( msg, userId );
    }
}
