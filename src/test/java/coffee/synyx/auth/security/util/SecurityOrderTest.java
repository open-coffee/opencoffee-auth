package coffee.synyx.auth.security.util;

import org.junit.Test;

import static coffee.synyx.auth.security.util.SecurityOrder.OVERRIDE_DEFAULT_ORDER;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.is;


/**
 * Unit test of {@link SecurityOrder}.
 *
 * @author  Tobias Schneider
 */
public class SecurityOrderTest {

    @Test
    public void correctOrderNumber() {

        assertThat(OVERRIDE_DEFAULT_ORDER, is(-102));
    }
}
