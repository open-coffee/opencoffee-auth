package coffee.synyx.auth.webtests.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

import net.thucydides.core.annotations.At;

import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;


/**
 * @author Tobias Schneider
 */
@DefaultUrl("http://coffeenet-auth-webtests.coffeenet:9999")
public class LoginPage extends PageObject {

    public void enterUsername(String username) {

        WebElementFacade usernameField = find(By.cssSelector("input#username[type=text]"));
        usernameField.type(username);
    }


    public void enterPassword(String password) {

        WebElementFacade passwordField = find(By.cssSelector("input#password[type=password]"));
        passwordField.type(password);
    }


    public void performLogin() {

        WebElementFacade loginButton = find(By.cssSelector("button[type=submit]"));
        loginButton.submit();
    }
}
