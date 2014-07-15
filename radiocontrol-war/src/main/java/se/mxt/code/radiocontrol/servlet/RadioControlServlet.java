package se.mxt.code.radiocontrol.servlet;

import java.io.BufferedReader;
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
            System.out.println("in doGet");
            RestRequest resourceValues = new RestRequest("GET", req.getPathInfo(), req.getReader());
            if (req.getParameter("cursor") != null) {
                resourceValues.setCursorParam(req.getParameter("cursor"));
            }
            RestEndpointDispatcher dispatcher = new RestEndpointDispatcher(resourceValues, resp);
            dispatcher.dispatch();
        } catch (ServletException e) {
            resp.setStatus(400);
            resp.resetBuffer();
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            System.out.println("in doPost");
            RestRequest resourceValues = new RestRequest("POST", req.getPathInfo(), req.getReader());
            RestEndpointDispatcher dispatcher = new RestEndpointDispatcher(resourceValues, resp);
            dispatcher.dispatch();
        } catch(ServletException e) {
            resp.setStatus(400);
            resp.resetBuffer();
            e.printStackTrace();
        }

    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            System.out.println("in doDelete");
            RestRequest resourceValues = new RestRequest("DELETE", req.getPathInfo(), req.getReader());
            RestEndpointDispatcher dispatcher = new RestEndpointDispatcher(resourceValues, resp);
            dispatcher.dispatch();
        } catch(ServletException e) {
            resp.setStatus(400);
            resp.resetBuffer();
            e.printStackTrace();
        }
    }
}
