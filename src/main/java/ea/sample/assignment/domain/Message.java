package ea.sample.assignment.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Message {

    private long id;
    private String message;
    private int score;
    private final long userId;

    public Message( long id, String message, int score, long userId ) {
        this.id = id;
        this.message = message;
        this.score = score;
        this.userId = userId;
    }

    protected Message() {
        // required by Jackson to deserialize object. This is the
        // only time the default constructor is used.
        userId = -1;
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
                .isEquals();
    }

    public void setScore( int score ) {
        this.score = score;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder( 17, 37 )
                .append( getId() )
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", score=" + score +
                ", userId=" + userId +
                '}';
    }

    public long getUserId() {
        return userId;
    }
}
