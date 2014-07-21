package se.mxt.code.radiocontrol;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import se.mxt.code.radiocontrol.api.RestResource;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

/**
 * Created by deejaybee on 7/21/14.
 */
@Entity
public class ProgramChannelOwner implements RestResource {
    @Id String email;
    String firstName;
    String lastName;
    boolean premiumUser;

    private ProgramChannelOwner() { }

    public ProgramChannelOwner(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getEmail() { return this.email; }
    public String getFirstName() { return this.firstName; }
    public String getLastName() { return this.lastName; }

    public JsonObject asJsonObject() {
        return Json.createObjectBuilder()
                .add("email", email)
                .add("firstname", (firstName != null) ? firstName : "")
                .add("lastname", (lastName != null) ? lastName : "")
                .build();
    }

    public static ProgramChannelOwner buildFromJson(String jsonString) {
        ProgramChannelOwner owner = new ProgramChannelOwner();
        owner.fromJson(jsonString);
        return owner;
    }

    @Override
    public String toJson() { return asJsonObject().toString(); }

    @Override
    public void fromJson(String jsonString) {
        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        JsonObject obj = jsonReader.readObject();
        jsonReader.close();

        email = obj.containsKey("email") ? obj.getString("email") : "";
        firstName = obj.containsKey("firstname") ? obj.getString("firstname") : "";
        lastName = obj.containsKey("lastname") ? obj.getString("lastname") : "";
    }

    @Override
    public String resourceIdentifier() { return "owner"; }
}
