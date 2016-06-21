package coffee.synyx.auth.security.util;

import org.springframework.boot.autoconfigure.security.SecurityProperties;


/**
 * @author  Yannic Klem - klem@synyx.de
 */
public final class SecurityOrder {

    public static final int OVERRIDE_DEFAULT_ORDER = SecurityProperties.DEFAULT_FILTER_ORDER - 2;
    public static final int ACTUATOR_ORDER = OVERRIDE_DEFAULT_ORDER + 1;

    private SecurityOrder() {
    }
}
