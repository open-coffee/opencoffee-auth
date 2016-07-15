package coffee.synyx.auth.web;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ServerProperties;

import org.springframework.http.HttpStatus;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentServletMapping;


/**
 * Controller for every error not caught by other mechanisms.
 *
 * @author  David Schilling - schilling@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class ErrorController extends BasicErrorController {

    @Autowired
    public ErrorController(ErrorAttributes errorAttributes, ServerProperties serverProperties) {

        super(errorAttributes, serverProperties.getError());
    }

    @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView modelAndView = super.errorHtml(request, response);

        if (response.getStatus() == FORBIDDEN.value()) {
            return handleSpecificError(FORBIDDEN, modelAndView);
        }

        if (response.getStatus() == NOT_FOUND.value()) {
            return handleSpecificError(NOT_FOUND, modelAndView);
        }

        return modelAndView;
    }


    private static ModelAndView handleSpecificError(HttpStatus httpStatus, ModelAndView modelAndView) {

        String pathAttribute = "path";
        String path = (String) modelAndView.getModel().get(pathAttribute);

        modelAndView.setViewName(String.valueOf(httpStatus.value()));
        modelAndView.addObject(pathAttribute, fromCurrentServletMapping().toUriString() + path);

        return modelAndView;
    }
}
