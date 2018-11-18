package ea.sample.assignment.dao;

import ea.sample.assignment.domain.User;

import java.util.Optional;
import java.util.Set;

public interface IUserRepository {

    User create( String name, String email );

    Set<User> readAll();

    Optional<User> read( long id );

    Set<String> readTopics( long userId );
}
