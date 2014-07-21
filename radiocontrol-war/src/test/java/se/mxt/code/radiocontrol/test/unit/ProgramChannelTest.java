package se.mxt.code.radiocontrol.test.unit;

import com.google.appengine.api.files.dev.Session;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.junit.*;
import org.junit.rules.ExpectedException;
import se.mxt.code.radiocontrol.ProgramChannel;
import se.mxt.code.radiocontrol.ProgramChannelOwner;
import se.mxt.code.radiocontrol.ProgramChannelService;
import se.mxt.code.radiocontrol.api.RestException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import java.io.StringReader;

import static se.mxt.code.radiocontrol.OfyService.ofy;

/**
 * Created by deejaybee on 7/17/14.
 */
public class ProgramChannelTest {

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
    public void createFromJson() throws RestException {
        String testJson = "{\"id\":5066549580791808,\"title\":\"Test Channel\",\"image\":\"http://upload.wikimedia.org/wikipedia/commons/0/02/NX1Z_Radio.jpg\",\"stream\":\"http://stream.test.com\"}";

        ProgramChannel channel = ProgramChannel.buildFromJson(testJson);

        Assert.assertEquals("Test Channel", channel.getChannelTitle());
        Assert.assertEquals("http://upload.wikimedia.org/wikipedia/commons/0/02/NX1Z_Radio.jpg", channel.getImageURL());
        Assert.assertEquals("http://stream.test.com", channel.getStreamURL());
    }

    @Test
    public void generateJson() {
        ProgramChannel channel = new ProgramChannel("Test Channel", "http://stream.test.com");
        channel.setImageURL("http://upload.wikimedia.org/wikipedia/commons/0/02/NX1Z_Radio.jpg");

        JsonReader jsonReader = Json.createReader(new StringReader(channel.toJson()));
        JsonObject obj = jsonReader.readObject();

        Assert.assertTrue(obj.containsKey("title"));
        Assert.assertTrue(obj.containsKey("stream"));
        Assert.assertTrue(obj.containsKey("image"));

        Assert.assertEquals("Test Channel", obj.getString("title"));
        Assert.assertEquals("http://stream.test.com", obj.getString("stream"));
        Assert.assertEquals("http://upload.wikimedia.org/wikipedia/commons/0/02/NX1Z_Radio.jpg", obj.getString("image"));
    }
}
