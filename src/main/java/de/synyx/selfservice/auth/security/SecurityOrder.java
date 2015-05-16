package de.synyx.selfservice.auth.security;

import org.springframework.boot.autoconfigure.security.SecurityProperties;


/**
 * Created by klem on 16.05.15.
 */
public class SecurityOrder {

    public static final int OVERRIDE_DEFAULT_ORDER = SecurityProperties.DEFAULT_FILTER_ORDER - 1;

    private SecurityOrder() {
    }
}
