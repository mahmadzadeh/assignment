package ea.sample.assignment.notification.exceptions;

public class UnableToFullfilException extends Exception {
    public UnableToFullfilException( String message ) {
        super( message );
    }

    public UnableToFullfilException( String message, Exception cause ) {
        super( message, cause );
    }
}
