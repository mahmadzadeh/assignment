package ea.sample.assignment.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Message {

    private long id;
    private String message;

    public Message( long id, String message ) {
        this.id = id;
        this.message = message;
    }

    protected Message() {
        // required by Jackson to deserialize object. This is the
        // only time the default constructor is used.
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;

        if ( !( o instanceof Message ) ) return false;

        Message message = (Message) o;

        return new EqualsBuilder()
                .append( getId(), message.getId() )
                .append( getMessage(), message.getMessage() )
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder( 17, 37 )
                .append( getId() )
                .append( getMessage() )
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}
