package se.mxt.code.radiocontrol;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import org.bff.javampd.Playlist;
import se.mxt.code.radiocontrol.servlet.RestResource;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import java.io.StringReader;
import java.util.Collection;

/**
 * Created by deejaybee on 7/11/14.
 */

@Entity
public class ProgramChannel implements RestResource {
    @Index String channelTitle;
    String streamURL;
    String imageURL;
    @Id Long channelID;

    private ProgramChannel() { }

    public ProgramChannel(String channelTitle, String streamURL) {
        this.channelTitle = channelTitle;
        this.streamURL = streamURL;
    }

    public String getChannelTitle() { return channelTitle; }
    public long getChannelID() { return channelID; }
    public String getStreamURL() { return streamURL; }
    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }


    public JsonObject asJsonObject() {
        return Json.createObjectBuilder()
                .add("id", (channelID != null) ? channelID : 0)
                .add("title", channelTitle)
                .add("image", (imageURL != null) ? imageURL : "")
                .add("stream", (streamURL != null) ? streamURL : "").build();
    }

    @Override
    public String toJson() {
        return asJsonObject().toString();
    }

    @Override
    public void fromJson(String jsonString) {
        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        JsonObject obj = jsonReader.readObject();
        jsonReader.close();

        channelTitle = obj.containsKey("title") ? obj.getString("title") : "";
        streamURL = obj.containsKey("stream") ? obj.getString("stream") : "";
        imageURL = obj.containsKey("image") ? obj.getString("image") : "";
    }

    public static ProgramChannel buildFromJson(String jsonString) {
        ProgramChannel channel = new ProgramChannel();
        channel.fromJson(jsonString);
        return channel;
    }
}
