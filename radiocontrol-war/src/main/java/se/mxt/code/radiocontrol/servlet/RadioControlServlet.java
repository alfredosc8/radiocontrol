package se.mxt.code.radiocontrol.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.mxt.code.radiocontrol.api.Dispatcher;
import se.mxt.code.radiocontrol.api.RestRequest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by deejaybee on 7/11/14.
 */
public class RadioControlServlet extends HttpServlet {
    private final static Logger LOG = LoggerFactory.getLogger(RadioControlServlet.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            LOG.debug("API doGet: " + req.getPathInfo());
            RestRequest resourceValues = new RestRequest("GET", req.getPathInfo(), req.getReader());
            if (req.getParameter("cursor") != null) {
                resourceValues.setCursorParam(req.getParameter("cursor"));
            }
            Dispatcher dispatcher = new Dispatcher(resourceValues, resp);
            dispatcher.dispatch();
        } catch (ServletException e) {
            resp.setStatus(400);
            resp.resetBuffer();
            LOG.error("Servlet Exception: ", e);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            LOG.debug("API doPost: " + req.getPathInfo());
            RestRequest resourceValues = new RestRequest("POST", req.getPathInfo(), req.getReader());
            Dispatcher dispatcher = new Dispatcher(resourceValues, resp);
            dispatcher.dispatch();
        } catch(ServletException e) {
            resp.setStatus(400);
            resp.resetBuffer();
            LOG.error("Servlet Exception: ", e);
        }

    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            LOG.debug("API doPut: " + req.getPathInfo());
            RestRequest resourceValues = new RestRequest("PUT", req.getPathInfo(), req.getReader());
            Dispatcher dispatcher = new Dispatcher(resourceValues, resp);
            dispatcher.dispatch();
        } catch(ServletException e) {
            resp.setStatus(400);
            resp.resetBuffer();
            LOG.error("Servlet Exception: ", e);
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            LOG.debug("API doDelete: " + req.getPathInfo());
            RestRequest resourceValues = new RestRequest("DELETE", req.getPathInfo(), req.getReader());
            Dispatcher dispatcher = new Dispatcher(resourceValues, resp);
            dispatcher.dispatch();
        } catch(ServletException e) {
            resp.setStatus(400);
            resp.resetBuffer();
            LOG.error("Servlet Exception: ", e);
        }
    }
}
