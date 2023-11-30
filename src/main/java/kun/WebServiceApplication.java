package kun;

import kun.connector.DatabaseConnector;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/share")
@WebListener
public class WebServiceApplication extends Application implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Initialize the database when the application starts
        DatabaseConnector.initialize();
    }
}