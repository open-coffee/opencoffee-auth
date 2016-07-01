package coffee.synyx.auth.web;

/**
 * @author  David Schilling - schilling@synyx.de
 */
public enum MessageType {

    ERROR("danger"),
    SUCCESS("success");

    private final String name;

    MessageType(String name) {

        this.name = name;
    }

    public String getName() {

        return name;
    }
}
