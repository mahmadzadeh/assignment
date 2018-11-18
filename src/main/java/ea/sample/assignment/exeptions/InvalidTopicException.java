package ea.sample.assignment.exeptions;

public class InvalidTopicException extends RuntimeException {
    public InvalidTopicException( String message ) {
        super( message );
    }

    public InvalidTopicException( String message, Exception cause ) {
        super( message, cause );
    }
}
