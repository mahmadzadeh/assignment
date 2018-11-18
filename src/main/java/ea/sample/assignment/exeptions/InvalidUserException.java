package ea.sample.assignment.exeptions;

public class InvalidUserException extends RuntimeException {
    public InvalidUserException( String message ) {
        super( message );
    }

    public InvalidUserException( String message, Exception cause ) {
        super( message, cause );
    }
}
