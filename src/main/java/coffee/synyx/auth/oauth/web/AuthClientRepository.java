package coffee.synyx.auth.oauth.web;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
@Repository
public interface AuthClientRepository extends CrudRepository<AuthClient, Long> {

    AuthClient findByClientId(String clientID);
}
