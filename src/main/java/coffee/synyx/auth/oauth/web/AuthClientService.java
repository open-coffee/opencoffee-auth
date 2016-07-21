package coffee.synyx.auth.oauth.web;

/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
public interface AuthClientService {

    /**
     * Returns a {@link AuthClient} Entity with the given id.
     *
     * @param  authClientId  of the entity to return
     *
     * @return  the searched {@link AuthClient}
     */
    AuthClient get(String authClientId);
}
