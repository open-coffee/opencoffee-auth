package coffee.synyx.auth.oauth.web;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import static java.lang.String.format;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
@Service
public class AuthClientServiceImpl implements AuthClientService {

    private AuthClientRepository authClientRepository;

    @Autowired
    public AuthClientServiceImpl(AuthClientRepository authClientRepository) {

        this.authClientRepository = authClientRepository;
    }

    @Override
    public AuthClient get(String clientId) {

        AuthClient authClient = authClientRepository.findByClientId(clientId);

        if (authClient == null) {
            throw new AuthClientNotFoundException(format("AuthClient with id %s was not found.", clientId));
        }

        return authClient;
    }
}
