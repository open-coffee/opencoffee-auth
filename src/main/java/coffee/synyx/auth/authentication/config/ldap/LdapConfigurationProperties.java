package coffee.synyx.auth.authentication.config.ldap;

import org.hibernate.validator.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;


/**
 * All properties that are required to configure an LDAP-based authentication.
 *
 * @author  Yannic Klem - klem@synyx.de
 */
@Validated
@ConfigurationProperties(prefix = "auth.ldap")
public class LdapConfigurationProperties {

    @NotBlank
    private String url = "ldap://localhost";

    @NotBlank
    private String base;

    @NotBlank
    private String userSearchBase = "ou=People";

    @NotBlank
    private String userSearchFilter = "uid={0}";

    @NotBlank
    private String groupSearchBase = "ou=Groups";

    @NotBlank
    private String groupSearchFilter = "member={0}";

    @NotBlank
    private String groupRoleAttribute = "cn";

    @NotBlank
    private String rolePrefix = "ROLE_";

    @NotNull
    private boolean connectionWithTls = true;

    public String getUrl() {

        return url;
    }


    public void setUrl(String url) {

        this.url = url;
    }


    public String getBase() {

        return base;
    }


    public void setBase(String base) {

        this.base = base;
    }


    public String getUserSearchBase() {

        return userSearchBase;
    }


    public void setUserSearchBase(String userSearchBase) {

        this.userSearchBase = userSearchBase;
    }


    public String getUserSearchFilter() {

        return userSearchFilter;
    }


    public void setUserSearchFilter(String userSearchFilter) {

        this.userSearchFilter = userSearchFilter;
    }


    public String getGroupSearchBase() {

        return groupSearchBase;
    }


    public void setGroupSearchBase(String groupSearchBase) {

        this.groupSearchBase = groupSearchBase;
    }


    public String getGroupSearchFilter() {

        return groupSearchFilter;
    }


    public void setGroupSearchFilter(String groupSearchFilter) {

        this.groupSearchFilter = groupSearchFilter;
    }


    public String getGroupRoleAttribute() {

        return groupRoleAttribute;
    }


    public void setGroupRoleAttribute(String groupRoleAttribute) {

        this.groupRoleAttribute = groupRoleAttribute;
    }


    public String getRolePrefix() {

        return rolePrefix;
    }


    public void setRolePrefix(String rolePrefix) {

        this.rolePrefix = rolePrefix;
    }


    public boolean isConnectionWithTls() {

        return connectionWithTls;
    }


    public void setConnectionWithTls(boolean connectionWithTls) {

        this.connectionWithTls = connectionWithTls;
    }
}
