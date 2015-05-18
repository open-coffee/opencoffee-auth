package de.synyx.selfservice.auth.security;

import org.springframework.boot.autoconfigure.security.SecurityProperties;


/**
 * Created by klem on 16.05.15.
 */
public final class SecurityOrder {

    public static final int OVERRIDE_DEFAULT_ORDER = SecurityProperties.DEFAULT_FILTER_ORDER - 2;
    public static final int ACTUATOR_ORDER = OVERRIDE_DEFAULT_ORDER + 1;

    private SecurityOrder() {
    }
}
