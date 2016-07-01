package coffee.synyx.auth.security.util;

import org.junit.Test;

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

        assertThat(SecurityOrder.OVERRIDE_DEFAULT_ORDER, is(-102));
    }
}
