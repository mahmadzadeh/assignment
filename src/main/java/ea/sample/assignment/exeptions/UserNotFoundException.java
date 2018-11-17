package ea.sample.assignment.exeptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
    }

    public UserNotFoundException( String message ) {
        super( message );
    }

    public UserNotFoundException( String message, Exception cause ) {
        super( message, cause );
    }
}
