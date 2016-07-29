package coffee.synyx.auth.oauth.web;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

/**
 * @author Yannic Klem - yann.klem@gmail.com
 */
public class ClientDetailsResourceValidator implements Validator{
    @Override
    public boolean supports(Class<?> aClass) {

        return ClientDetailsResource.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        ClientDetailsResource clientDetailsResource = (ClientDetailsResource) o;

        validateUrls(clientDetailsResource.getRegisteredRedirectUri(), errors);
    }

    private void validateUrls(String registeredRedirectUri, Errors errors) {

        Set<String> urls = StringUtils.commaDelimitedListToSet(registeredRedirectUri);

        for(String url : urls) {

            if(!url.matches("^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")){
                errors.rejectValue("registeredRedirectUri", "error.validation.clientdetails.uri");
                return;
            }
        }
    }
}
