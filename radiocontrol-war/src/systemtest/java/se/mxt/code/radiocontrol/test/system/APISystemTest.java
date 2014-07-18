package se.mxt.code.radiocontrol.test.system;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.junit.Assert;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by deejaybee on 7/18/14.
 */
public class APISystemTest {
    private static String endpoint = "http://localhost:8080/api/v1";

    @Test
    public void possibleToCreateNewChannel() {
        // it should be possible to create a new channel
        String testJson = "{\"title\":\"Test Channel\",\"image\":\"http://upload.wikimedia.org/wikipedia/commons/0/02/NX1Z_Radio.jpg\",\"stream\":\"http://stream.test.com\"}";

        try {
            Content data = Request.Post(endpoint + "/channel")
                    .bodyString(testJson, ContentType.APPLICATION_JSON)
                    .execute().returnContent();

            JsonReader jsonReader = Json.createReader(new StringReader(data.asString()));
            JsonObject obj = jsonReader.readObject();
            jsonReader.close();

            Assert.assertTrue(obj.containsKey("id"));
            Long channelId = Long.parseLong(obj.getString("id"));
            Assert.assertNotNull(channelId);

            data = Request.Get(endpoint + "/channel/" + channelId).execute().returnContent();
            jsonReader = Json.createReader(new StringReader(data.asString()));
            obj = jsonReader.readObject();

            Assert.assertTrue(obj.containsKey("title"));
            Assert.assertTrue(obj.containsKey("image"));
            Assert.assertTrue(obj.containsKey("stream"));
            Assert.assertEquals("Test Channel", obj.getString("title"));

        } catch (IOException e) {
            System.err.println("Failed to connect to API. Is the test system up and running? " + e.getMessage());
        }
    }
}
