package ea.sample.assignment.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.Collections.unmodifiableList;

public class Topic {

    private final long id;

    private final String name;
    private final List<Long> messages;

    public Topic( long id, String name ) {
        this.id = id;
        this.name = name;
        messages = new CopyOnWriteArrayList<>();
    }

    protected Topic() {
        id = 0;
        name = "";
        messages = new CopyOnWriteArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addMessage( long msg ) {
        messages.add( msg );
    }

    public List<Long> getMessages() {
        return unmodifiableList( messages );
    }

    public List<Long> getLastN( int n ) {

        return n > messages.size()
                ? unmodifiableList( messages )
                : unmodifiableList( messages.subList( messages.size() - n, messages.size() ) );
    }

    public long getId() {
        return id;
    }


    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;

        if ( !( o instanceof Topic ) ) return false;

        Topic topic = (Topic) o;

        return new EqualsBuilder()
                .append( getName(), topic.getName() )
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder( 17, 37 )
                .append( getName() )
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", messages=" + messages +
                '}';
    }
}
