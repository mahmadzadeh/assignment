package ea.sample.assignment;

import ea.sample.assignment.domain.User;

import java.util.Optional;
import java.util.Set;

public interface IUserRepository {

    User create( String name, String email );

    Set<User> readAll();

    Optional<User> read( long id );

}
