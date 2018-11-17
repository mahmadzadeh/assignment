package ea.sample.assignment.dao;

import ea.sample.assignment.IUserRepository;
import ea.sample.assignment.domain.Message;
import ea.sample.assignment.exeptions.UserNotFoundException;
import ea.sample.assignment.util.IdGenerator;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MessageRepository implements IMessageRepository {

    private final ConcurrentHashMap<Long, Message> inMemDb = new ConcurrentHashMap<>();

    private final IUserRepository userRepository;

    public MessageRepository( IUserRepository userRepository ) {
        this.userRepository = userRepository;
    }

    @Override
    public Message create( String msg, int score, long userId ) {

        if ( !userRepository.read( userId ).isPresent() ) {
            throw new UserNotFoundException( "Unable to create message. Invalid user Id given " + userId );
        }

        Message message = new Message( IdGenerator.nextId(), msg, 0, userId );
        inMemDb.put( message.getId(), message );

        return message;
    }

    @Override
    public Set<Message> readAll() {
        return null;
    }

    @Override
    public Optional<Message> read( long id ) {
        return Optional.empty();
    }
}
