package group0110.googleAuth;

/**
 * An Exception thrown when the GoogleAuthenticator fails login
 */
public class GoogleAuthorizationException extends Exception {
    public GoogleAuthorizationException(String errorMessage) {
        super(errorMessage);
    }
}
