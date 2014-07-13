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

    public ProgramChannel(String channelTitle, PlaylistPlayer player) {
        this.channelTitle = channelTitle;
        this.player = player;
    }

    public PlaylistPlayer getPlayer() {
        return player;
    }

    public String getChannelTitle() { return channelTitle; }

    public JsonObject asJsonObject() {
        return Json.createObjectBuilder()
                .add("title", channelTitle).build();
    }
}
