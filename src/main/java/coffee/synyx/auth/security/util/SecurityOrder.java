package coffee.synyx.auth.security.util;

import org.springframework.boot.autoconfigure.security.SecurityProperties;


/**
 * TODO.
 *
 * @author  Yannic Klem - klem@synyx.de
 */
public final class SecurityOrder {

    public static final int OVERRIDE_DEFAULT_ORDER = SecurityProperties.DEFAULT_FILTER_ORDER - 2;

    private SecurityOrder() {
    }
}
