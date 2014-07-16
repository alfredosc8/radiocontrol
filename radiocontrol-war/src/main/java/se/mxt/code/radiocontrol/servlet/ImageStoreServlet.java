package se.mxt.code.radiocontrol.servlet;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by deejaybee on 7/16/14.
 */
public class ImageStoreServlet extends HttpServlet {
    private String filename = null;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        System.out.println("Imagestore doGet: " + req.getPathInfo());

        if (req.getParameter("flowFilename") != null) {
            filename = req.getParameter("flowFilename");
            resp.setStatus(404);
            resp.resetBuffer();
        } else if (req.getPathInfo().contains("/images/")) {
            Pattern p = Pattern.compile("/images/(.*?)$");
            Matcher m = p.matcher(req.getPathInfo());

            if (m.matches()) {
                ImageStore imageStore = ImageStoreFactory.getService();
                String imageURL = imageStore.getURL(m.group(1));
                if (imageURL != null) {
                    resp.setStatus(200);
                    JsonObject obj = Json.createObjectBuilder()
                            .add("imageurl", imageURL)
                            .build();
                    resp.getWriter().write(obj.toString());
                } else {
                    resp.setStatus(404);
                }
            }
        }

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("Imagestore doPost: " + req.getPathInfo());

        ImageStore imageStore = ImageStoreFactory.getService();
        imageStore.storeImage(filename, req.getInputStream());
        resp.setStatus(200);
        filename = null;
    }
}