package ea.sample.assignment.util;

import ea.sample.assignment.domain.User;

public class ObserverUser {

    private final User user;

    public ObserverUser( User user ) {

        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
