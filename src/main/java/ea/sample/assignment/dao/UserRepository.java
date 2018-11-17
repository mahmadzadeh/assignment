package ea.sample.assignment.dao;

import ea.sample.assignment.domain.Topic;
import ea.sample.assignment.domain.User;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class UserRepository implements IUserRepository {
    @Override
    public User create( String name, String email ) {
        return null;
    }

    @Override
    public Set<User> readAll() {
        return null;
    }

    @Override
    public Optional<User> read( long id ) {
        return Optional.empty();
    }

    @Override
    public Set<Topic> readTopics( long userId ) {
        return null;
    }
}
