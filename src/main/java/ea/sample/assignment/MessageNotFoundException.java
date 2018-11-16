package ea.sample.assignment;

public class MessageNotFoundException extends RuntimeException {

    public MessageNotFoundException() {
    }

    public MessageNotFoundException( String message ) {
        super( message );
    }

    public MessageNotFoundException( String message, Throwable cause ) {
        super( message, cause );
    }
}