package coffee.synyx.auth.webtests.steps;

import coffee.synyx.auth.webtests.pages.LoginPage;

import net.thucydides.core.annotations.Step;

import static org.hamcrest.Matchers.containsString;


/**
 * @author Tobias Schneider
 */
public class LoginSteps {

    private LoginPage loginPage;

    @Step
    public void openLoginPage() {

        loginPage.open();
    }


    @Step
    public void login(String username, String password) {

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.performLogin();
    }

}
