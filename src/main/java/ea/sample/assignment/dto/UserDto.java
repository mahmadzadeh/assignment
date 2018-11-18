package ea.sample.assignment.dto;

import ea.sample.assignment.domain.User;

import java.util.Set;

public class UserDto {

    private final long id;
    private final String name;
    private final String email;
    private final int ranking;
    private Set<String> subscribedTopics;

    public UserDto( User user ) {

        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.ranking = user.getRanking();
        this.subscribedTopics = user.getSubscribedTopics();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getRanking() {
        return ranking;
    }
}
