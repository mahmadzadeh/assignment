package ea.sample.assignment.dao;

import ea.sample.assignment.domain.Message;
import ea.sample.assignment.exeptions.UserNotFoundException;
import ea.sample.assignment.util.IdGenerator;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class MessageRepository implements IMessageRepository {

    public static final int DEFAULT_STARTING_SCORE = 0;
    private final ConcurrentHashMap<Long, Message> inMemDb = new ConcurrentHashMap<>();

    private final IUserRepository userRepository;

    public MessageRepository( IUserRepository userRepository ) {
        this.userRepository = userRepository;
    }

    @Override
    public Message create( String msg, long userId ) {

        if ( !userRepository.read( userId ).isPresent() ) {
            throw new UserNotFoundException( "Unable to create message. Invalid user Id given " + userId );
        }

        Message message = new Message( IdGenerator.nextId(), msg, DEFAULT_STARTING_SCORE, userId );

        inMemDb.put( message.getId(), message );

        return message;
    }

    @Override
    public Set<Message> readAll() {
        return inMemDb
                .values()
                .stream().collect( Collectors.toSet() );
    }

    @Override
    public Optional<Message> read( long id ) {
        return Optional.ofNullable( inMemDb.get( id ) );
    }

    @Override
    public Set<Message> readMessagesForUser( long userId ) {
        return inMemDb.values()
                .stream()
                .filter( message -> message.getUserId() == userId )
                .collect( Collectors.toSet() );
    }

}
