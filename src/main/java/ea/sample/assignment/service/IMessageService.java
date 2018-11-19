package ea.sample.assignment.service;

import ea.sample.assignment.domain.Message;

import java.util.Set;

public interface IMessageService {
    Set<Message> getMessages();

    Message getMessage( long id );

    Set<Message> getUserMessages( long userId );

    Message createMessage( String msg, long userId );
}
