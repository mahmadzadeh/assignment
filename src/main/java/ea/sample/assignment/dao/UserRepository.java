package ea.sample.assignment.dao;

import ea.sample.assignment.domain.User;
import ea.sample.assignment.exeptions.DuplicateUserException;
import ea.sample.assignment.util.IdGenerator;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class UserRepository implements IUserRepository {
    private final ConcurrentHashMap<Long, User> inMemDb = new ConcurrentHashMap<>();

    @Override
    public User create( String name, String email ) {

        if ( isUserInSystem( email ) ) {
            throw new DuplicateUserException( "User with " + email + " already exist" );
        }

        User user = new User( IdGenerator.nextId(), name, email, 0 );

        inMemDb.put( user.getId(), user );

        return user;
    }

    @Override
    public Set<User> readAll() {
        return inMemDb.values().stream().collect( Collectors.toSet() );
    }

    @Override
    public Optional<User> read( long id ) {
        return Optional.ofNullable( inMemDb.get( id ) );
    }

    @Override
    public Set<String> readTopics( long userId ) {
        return inMemDb
                .get( userId )
                .getSubscribedTopics();
    }

    @Override
    public int size() {
        return this.inMemDb.size();
    }

    @Override
    public List<User> readTopRanked( int topN ) {

        List<User> collect = inMemDb.values().stream().collect( Collectors.toList() );

        Collections.sort( collect );

        return collect.size() > topN ? collect.subList( 0, topN ) : collect;
    }

    private boolean isUserInSystem( String email ) {
        return
                inMemDb.values()
                        .stream()
                        .map( u -> u.getEmail() )
                        .collect( Collectors.toSet() )
                        .contains( email );

    }
}
