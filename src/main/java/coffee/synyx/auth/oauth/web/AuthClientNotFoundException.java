package coffee.synyx.auth.oauth.web;

/**
 * Exception will be thrown if a requested {@link AuthClient} was not found.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
class AuthClientNotFoundException extends RuntimeException {

    AuthClientNotFoundException(String message) {

        super(message);
    }
}
