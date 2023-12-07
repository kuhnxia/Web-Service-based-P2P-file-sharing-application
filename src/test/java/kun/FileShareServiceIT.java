package kun;

import kun.connector.DatabaseConnector;
import kun.dao.SharedFileDao;
import kun.entity.SharedFile;
import kun.exceptions.ConflictException;
import kun.exceptions.NotModifiedException;
import kun.service.FileShareService;

import static org.junit.Assert.assertEquals;
import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Run integration tests with Arquillian to be able to test CDI beans
 */
@RunWith(Arquillian.class)
public class FileShareServiceIT {

    @Deployment
    public static WebArchive createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "FileShareServiceIT.war")
                .addClass(FileShareService.class)
                .addClass(FileShareServiceIT.class)
                .addClass(SharedFileDao.class)
                .addClass(SharedFile.class)
                .addClass(DatabaseConnector.class)
                .addClass(NotModifiedException.class)
                .addClass(ConflictException.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    FileShareService service;

    @Test
    public void testService() {
        String result = service.hello("World");
        assertEquals("Hello 'World'.", result);

        result = service.hello("Monde");
        assertEquals("Hello 'Monde'.", result);
    }
}