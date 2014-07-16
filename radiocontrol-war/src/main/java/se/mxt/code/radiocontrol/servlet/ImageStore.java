package se.mxt.code.radiocontrol.servlet;


import com.google.appengine.api.appidentity.AppIdentityService;
import com.google.appengine.api.appidentity.AppIdentityServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.tools.cloudstorage.*;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.util.List;
import java.util.Map;

/**
 * Created by deejaybee on 7/16/14.
 */
public class ImageStore {
    private final String defaultBucket = AppIdentityServiceFactory.getAppIdentityService().getDefaultGcsBucketName();
    private final GcsService gcsService = GcsServiceFactory.createGcsService(RetryParams.getDefaultInstance());
    private static final int BUFFER_SIZE = 2 * 1024 * 1024;

    private void copy(InputStream input, OutputStream output) throws IOException {
        try {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = input.read(buffer);
            while (bytesRead != -1) {
                output.write(buffer, 0, bytesRead);
                bytesRead = input.read(buffer);
            }
        } finally {
            input.close();
            output.close();
        }
    }

    public void storeImage(String fileName, InputStream in) throws IOException {
        System.out.println("Storing image with filename: " + fileName + " to " + defaultBucket);
        GcsFilename gcsFilename = new GcsFilename(defaultBucket, fileName);

        GcsOutputChannel outputChannel = gcsService.createOrReplace(gcsFilename, GcsFileOptions.getDefaultInstance());
        copy(in, Channels.newOutputStream(outputChannel));
    }

    public String getURL(String imageFileName) {
        try {
            ImagesService is = ImagesServiceFactory.getImagesService();
            String filename = String.format("/gs/%s/%s", defaultBucket, imageFileName);
            String url = is.getServingUrl(ServingUrlOptions.Builder.withGoogleStorageFileName(filename));
            return url;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
