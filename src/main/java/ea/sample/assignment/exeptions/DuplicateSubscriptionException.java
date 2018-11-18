package ea.sample.assignment.exeptions;

public class DuplicateSubscriptionException extends RuntimeException {
    public DuplicateSubscriptionException( String message ) {
        super( message );
    }

    public DuplicateSubscriptionException( String message, Exception cause ) {
        super( message, cause );
    }
}
