package coffee.synyx.auth.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author  Yannic Klem - klem@synyx.de
 */
@ConfigurationProperties(prefix = "ldap")
public class LdapConfigurationProperties {

    private String userSearchBase = "ou=accounts";

    private String userSearchFilter = "uid={0}";

    private String groupSearchBase = "ou=roles,ou=groups";

    private String groupSearchFilter = "member={0}";

    private String url = "ldap://ldap-slave.synyx.coffee:389";

    private String base = "dc=synyx,dc=coffee";

    private String rolePrefix = "ROLE_";

    private String groupRoleAttribute = "cn";

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
}
