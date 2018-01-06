package coffee.synyx.auth.webtests.features.login;

import coffee.synyx.auth.webtests.steps.LoginSteps;

import net.serenitybdd.junit.runners.SerenityRunner;

import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.openqa.selenium.WebDriver;


/**
 * @author  Tobias Schneider
 */
@RunWith(SerenityRunner.class)
public class WhenTryingToLogin {

    @Managed(uniqueSession = true)
    private WebDriver webDriver;

    @Steps
    private LoginSteps loginSteps;

    @Test
    public void authorizedUserWillBeLoggedIn() {

        // given
        loginSteps.openLoginPage();

        // when
        loginSteps.login("admin", "admin");

        // then
        // assertThat(webDriver.getCurrentUrl(), containsString("login"));
    }


    @Test
    public void unauthorizedUserWillNotBeLoggedIn() {

        // given
        loginSteps.openLoginPage();

        // when
        loginSteps.login("admin", "wrongPassword");

        // then
        // assertThat(webDriver.getCurrentUrl(), containsString("login"));
    }
}
