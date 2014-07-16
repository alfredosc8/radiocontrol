package se.mxt.code.radiocontrol.servlet;

/**
 * Created by deejaybee on 7/16/14.
 */
public class ImageStoreFactory {
    private static ImageStore imageStoreInstance = null;

    protected ImageStoreFactory() {}

    public static ImageStore getService() {
        if (imageStoreInstance == null) {
            imageStoreInstance = new ImageStore();
        }
        return imageStoreInstance;
    }
}
