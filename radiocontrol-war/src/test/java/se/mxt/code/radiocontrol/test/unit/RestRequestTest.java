package se.mxt.code.radiocontrol.test.unit;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.junit.*;
import org.junit.rules.ExpectedException;
import se.mxt.code.radiocontrol.api.RestException;
import se.mxt.code.radiocontrol.api.RestRequest;

import javax.servlet.ServletException;

import static se.mxt.code.radiocontrol.OfyService.ofy;

/**
 * Created by deejaybee on 7/17/14.
 */
public class RestRequestTest {

    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void parseRequestString() {
        try {
            RestRequest resource = new RestRequest("GET", "/channel/1234567", null);
            Assert.assertEquals(new Long(1234567), resource.getId());
            Assert.assertEquals("channel", resource.getResourceType());
            Assert.assertEquals("GET", resource.getAction());
        } catch (ServletException e) {

        }
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void parseInvalidRequestString() {
        try {
            RestRequest resource = new RestRequest("GET", "/channel/stuff/1234567", null);
            exception.expect(ServletException.class);
            exception.expectMessage("Invalid URI");
        } catch (ServletException e) {

        }
    }

    @Test
    public void parseInvalidResource() {
        try {
            RestRequest resource = new RestRequest("GET", "/channels/1234567", null);
            exception.expect(RestException.class);
            exception.expectMessage("channels is not a valid resource");
        } catch (ServletException e) {
        }
    }
}
