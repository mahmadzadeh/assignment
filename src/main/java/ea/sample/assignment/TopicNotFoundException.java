package ea.sample.assignment;

public class TopicNotFoundException extends RuntimeException {

    public TopicNotFoundException() {
    }

    public TopicNotFoundException( String message ) {
        super( message );
    }

    public TopicNotFoundException( String message, Exception cause ) {
        super( message, cause );
    }
}
