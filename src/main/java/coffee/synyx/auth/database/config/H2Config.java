package coffee.synyx.auth.database.config;

import org.h2.tools.Server;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

import static java.lang.String.valueOf;


/**
 * Configuration of the h2 database for developers.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
@Configuration
@ConfigurationProperties(prefix = "h2-console")
public class H2Config {

    private int port;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2WebServer() throws SQLException {

        return Server.createWebServer("-web", "-webAllowOthers", "-webPort", valueOf(port));
    }


    public void setPort(int port) {

        this.port = port;
    }


    public int getPort() {

        return port;
    }
}
