package ea.sample.assignment.dto;

import ea.sample.assignment.domain.User;

import java.util.Collections;
import java.util.Set;

public class UserDto {

    private long id;
    private String name;
    private String email;
    private int ranking;

    private Set<String> subscribedTopics;

    public UserDto( User user ) {

        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.ranking = user.getRanking();
        this.subscribedTopics = user.getSubscribedTopics();
    }

    public UserDto( long id, String name, String email, int ranking, Set<String> subscribedTopics ) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.ranking = ranking;
        this.subscribedTopics = subscribedTopics;
    }

    protected UserDto() {
        // to keep jackson happy
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

    public Set<String> getSubscribedTopics() {
        return Collections.unmodifiableSet( subscribedTopics );
    }
}
