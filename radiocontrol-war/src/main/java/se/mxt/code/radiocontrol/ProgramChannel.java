package se.mxt.code.radiocontrol;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.Collection;

/**
 * Created by deejaybee on 7/11/14.
 */
public class ProgramChannel {
    private String channelTitle;
    private PlaylistPlayer player;
    private String streamURL;
    private String imageURL;

    public ProgramChannel(String channelTitle, PlaylistPlayer player, String streamURL) {
        this.channelTitle = channelTitle;
        this.player = player;
        this.streamURL = streamURL;
    }

    public PlaylistPlayer getPlayer() {
        return player;
    }

    public String getChannelTitle() { return channelTitle; }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public JsonObject asJsonObject() {
        return Json.createObjectBuilder()
                .add("title", channelTitle)
                .add("image", imageURL)
                .add("stream", streamURL).build();
    }

    public String toJson() {
        return asJsonObject().toString();
    }
}
