package coffee.synyx.auth.web;

import org.springframework.web.servlet.ModelAndView;

import static coffee.synyx.auth.web.MessageType.ERROR;


/**
 * Dto for messages which are used to show up in the frontend as information for the user after successful/error
 * actions.
 *
 * @author  David Schilling - schilling@synyx.de
 */
public final class Message {

    private static final String ATTRIBUTE_NAME = "message";
    private final String messageKey;
    private final MessageType type;
    private final String messageValue;

    private Message(MessageType type, String messageValue, String messageKey) {

        this.messageKey = messageKey;
        this.type = type;
        this.messageValue = messageValue;
    }

    public String getMessageKey() {

        return messageKey;
    }


    public String getType() {

        return type.getName();
    }


    public String getMessageValue() {

        return messageValue;
    }


    public static void addErrorCode(ModelAndView modelAndView, String messageKey) {

        modelAndView.addObject(ATTRIBUTE_NAME, new Message(ERROR, null, messageKey));
    }
}
