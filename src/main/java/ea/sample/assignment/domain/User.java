package ea.sample.assignment.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import static java.util.Collections.unmodifiableSet;

public class User implements Comparable<User> {

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

    public int getRanking() {
        return ranking;
    }

    public boolean isAlreadySubscribed( String name ) {
        return this.subscribedTopics.contains( name );
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;

        if ( !( o instanceof User ) ) return false;

        User user = (User) o;

        return new EqualsBuilder()
                .append( getEmail(), user.getEmail() )
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder( 17, 37 )
                .append( getEmail() )
                .toHashCode();
    }

    @Override
    public int compareTo( User other ) {
        return -( ranking - other.ranking );
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", ranking=" + ranking +
                ", subscribedTopics=" + subscribedTopics +
                '}';
    }
}
