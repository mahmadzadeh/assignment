package ea.sample.assignment.dao;

import ea.sample.assignment.domain.Message;

import java.util.Optional;
import java.util.Set;

public interface IMessageRepository {

    Message create( String msg, int score, long userId );

    Set<Message> readAll();

    Optional<Message> read( long id );

    Set<Message> readMessagesForUser( long userId );
}
