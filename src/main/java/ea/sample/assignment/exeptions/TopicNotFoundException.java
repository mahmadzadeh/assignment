package ea.sample.assignment.exeptions;

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
