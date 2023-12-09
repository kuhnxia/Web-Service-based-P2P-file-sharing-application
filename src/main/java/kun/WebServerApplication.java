package kun;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
/**
 * Jboss Application configuration for the web server.
 *
 * This class defines the base path for the WildFly web service provided
 * by the application.
 *
 * @author Kun Xia
 */
@ApplicationPath("/share")
public class WebServerApplication extends Application {
}