package se.mxt.code.radiocontrol.test.unit;

import com.google.appengine.api.files.dev.Session;
import org.junit.Assert;
import org.junit.Test;
import se.mxt.code.radiocontrol.ProgramChannel;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

/**
 * Created by deejaybee on 7/17/14.
 */
public class ProgramChannelTest {

    @Test
    public void createFromJson() {
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
