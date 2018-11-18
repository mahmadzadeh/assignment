package ea.sample.assignment.dto;

import ea.sample.assignment.domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserCollectionDto {

    private final List<User> users;

    public UserCollectionDto( Collection<User> users ) {
        this.users = new ArrayList<>( users );
    }

    public List<User> getUsers() {
        return Collections.unmodifiableList( this.users );
    }

    public int size() {
        return users.size();
    }
}
