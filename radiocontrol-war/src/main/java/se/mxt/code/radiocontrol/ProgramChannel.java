package se.mxt.code.radiocontrol;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import com.googlecode.objectify.annotation.Load;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.mxt.code.radiocontrol.api.RestException;
import se.mxt.code.radiocontrol.api.RestResource;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

import static se.mxt.code.radiocontrol.OfyService.ofy;

/**
 * Created by deejaybee on 7/11/14.
 */

@Entity
public class ProgramChannel implements RestResource {
    private final static Logger LOG = LoggerFactory.getLogger(ProgramChannel.class);

    @Index String channelTitle;
    String streamURL;
    String imageURL;
    @Id Long channelID;
    @Load @Index Ref<ProgramChannelOwner> channelOwnerRef;

    private ProgramChannel() { }

    public ProgramChannel(String channelTitle, String streamURL) {
        this.channelTitle = channelTitle;
        this.streamURL = streamURL;
    }

    public String getChannelTitle() { return channelTitle; }
    public Long getChannelID() { return channelID; }
    public String getStreamURL() { return streamURL; }
    public String getImageURL() {
        return imageURL;
    }
    public ProgramChannelOwner getOwner() {
        if (channelOwnerRef != null) {
            return channelOwnerRef.get();
        }
        return null;
    }

    public void setChannelID(Long id) {
        this.channelID = id;
    }
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
    public void setOwner(ProgramChannelOwner owner) {
        channelOwnerRef = Ref.create(Key.create(ProgramChannelOwner.class, owner.getEmail()));
    }


    public JsonObject asJsonObject() {
        String ownerId = null;
        ProgramChannelOwner pco = getOwner();
        if (pco != null) {
            ownerId = pco.getEmail();
        }
        return Json.createObjectBuilder()
                .add("id", (channelID != null) ? channelID : 0)
                .add("ownerid", (ownerId != null) ? ownerId : "")
                .add("title", channelTitle)
                .add("image", (imageURL != null) ? imageURL : "")
                .add("stream", (streamURL != null) ? streamURL : "").build();
    }

    public static ProgramChannel buildFromJson(String jsonString) throws RestException {
        ProgramChannel channel = new ProgramChannel();
        channel.fromJson(jsonString);
        return channel;
    }

    @Override
    public String toJson() {
        return asJsonObject().toString();
    }

    @Override
    public void fromJson(String jsonString) throws RestException {
        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        JsonObject obj = jsonReader.readObject();
        jsonReader.close();

        if (obj.containsKey("ownerid")) {
            ProgramChannelOwner owner = ProgramChannelService.getOwnerById(obj.getString("ownerid"));
            if (owner != null) {
                setOwner(owner);
            } else {
                owner = new ProgramChannelOwner(obj.getString("ownerid"), "NO", "NAME");
                ProgramChannelService.saveOwner(owner);
                setOwner(owner);
            }
        }
        channelTitle = obj.containsKey("title") ? obj.getString("title") : "";
        streamURL = obj.containsKey("stream") ? obj.getString("stream") : "";
        imageURL = obj.containsKey("image") ? obj.getString("image") : "";
    }

    @Override
    public String resourceIdentifier() {
        return "channel";
    }

}
