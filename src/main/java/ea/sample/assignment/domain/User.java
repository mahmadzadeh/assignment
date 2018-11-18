package ea.sample.assignment.domain;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import static java.util.Collections.unmodifiableSet;

public class User {

    private final long id;
    private final String name;
    private final String email;
    private final int ranking;

    private Set<String> subscribedTopics;

    public User( long id, String name, String email, int ranking ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.ranking = ranking;
        this.subscribedTopics = new CopyOnWriteArraySet<>();
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

    public void subscribe( String topicName ) {
        subscribedTopics.add( topicName );
    }

    public Set<String> getSubscribedTopics() {
        return unmodifiableSet( this.subscribedTopics );
    }
}
