package ea.sample.assignment.domain;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class User {

    private final long id;
    private final String name;
    private final String email;
    private Set<String> topicSubs;

    public User( long id, String name, String email ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.topicSubs = new CopyOnWriteArraySet<>();
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
        topicSubs.add( topicName );
    }
}
