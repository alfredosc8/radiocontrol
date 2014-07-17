package se.mxt.code.radiocontrol.api;

/**
 * Created by deejaybee on 7/17/14.
 */
public interface RestResource {
    public String toJson();
    public void fromJson(String jsonString);
    public String resourceIdentifier();
}
