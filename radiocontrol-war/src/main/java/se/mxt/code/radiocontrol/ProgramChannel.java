package se.mxt.code.radiocontrol;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import org.bff.javampd.Playlist;

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
public class ProgramChannel {
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

    public long getChannelID() {
        return channelID;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public JsonObject asJsonObject() {
        return Json.createObjectBuilder()
                .add("id", (channelID != null) ? channelID : 0)
                .add("title", channelTitle)
                .add("image", (imageURL != null) ? imageURL : "")
                .add("stream", streamURL).build();
    }

    public String toJson() {
        return asJsonObject().toString();
    }

    public static ProgramChannel fromJson(String jsonString) {
        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        JsonObject obj = jsonReader.readObject();

        ProgramChannel channel = new ProgramChannel(obj.getString("title"), obj.getString("stream"));
        return channel;
    }
}
