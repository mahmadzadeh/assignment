package ea.sample.assignment.exeptions;

public class DuplicateTopicException extends RuntimeException {
    public DuplicateTopicException() {
    }

    public DuplicateTopicException( String message ) {
        super( message );
    }

    public DuplicateTopicException( String message, Exception cause ) {
        super( message, cause );
    }
}
