package se.mxt.code.radiocontrol.servlet;

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by deejaybee on 7/11/14.
 */
public class RadioControlServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            RestRequest resourceValues = new RestRequest("GET", req.getPathInfo());
            RestEndpointDispatcher dispatcher = new RestEndpointDispatcher(resourceValues, resp);
            dispatcher.dispatch();
        } catch (ServletException e) {
            resp.setStatus(400);
            resp.resetBuffer();
            e.printStackTrace();
        }
    }
}
