package kun;

import static org.junit.Assert.assertEquals;

import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Run integration tests with Arquillian to be able to test CDI beans
 */
@RunWith(Arquillian.class)
public class  GettingStartedServiceIT {

    @Deployment
    public static WebArchive createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "GettingStartedServiceIT.war")
                .addClass(GettingStartedService.class);
    }

    @Inject
    GettingStartedService service;

    @Test
    public void testService() {
        String result = service.hello("World");
        assertEquals("Hello 'World'.", result);

        result = service.hello("Monde");
        assertEquals("Hello 'Monde'.", result);
    }
}