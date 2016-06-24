package coffee.synyx.auth.user;

import org.springframework.security.core.userdetails.UserDetails;

public class SynyxAuthentication {

    private String id;
    private String name;
    private UserDetails userDetails;


    public SynyxAuthentication(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
