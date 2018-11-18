package ea.sample.assignment.exeptions;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException() {
    }

    public DuplicateUserException( String message ) {
        super( message );
    }

    public DuplicateUserException( String message, Exception cause ) {
        super( message, cause );
    }
}
