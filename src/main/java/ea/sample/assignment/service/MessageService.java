package ea.sample.assignment.service;

import ea.sample.assignment.dao.IMessageRepository;
import ea.sample.assignment.domain.Message;
import ea.sample.assignment.exeptions.TopicNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class MessageService {

    private final IMessageRepository messageRepository;

    public MessageService( IMessageRepository messageRepository ) {
        this.messageRepository = messageRepository;
    }

    public Set<Message> getMessages() {
        return messageRepository.readAll();
    }

    public Message getMessage( long id ) {
        Optional<Message> message = messageRepository.read( id );

        return message.orElseThrow( () -> new TopicNotFoundException( "Unable to find topic with id " + id ) );
    }

    public Set<Message> getUserMessages( long userId ) {
        return messageRepository.readMessagesForUser( userId );
    }

    public Message createMessage( String msg, long userId ) {

        return messageRepository.create( msg, 0, userId );
    }
}
