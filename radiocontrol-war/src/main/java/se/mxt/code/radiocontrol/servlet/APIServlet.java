package se.mxt.code.radiocontrol.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.mxt.code.radiocontrol.api.Dispatcher;
import se.mxt.code.radiocontrol.api.RestRequest;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by deejaybee on 7/11/14.
 */
public class APIServlet extends HttpServlet {
    private final static Logger LOG = LoggerFactory.getLogger(APIServlet.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            LOG.debug("API doGet: " + req.getPathInfo());
            Enumeration params = req.getParameterNames();
            while(params.hasMoreElements()) {
                String p = (String)params.nextElement();
                LOG.debug(" - " + p + "=" + req.getParameter(p));
            }
            RestRequest resourceValues = new RestRequest("GET", req.getPathInfo(), req.getReader());
            if (req.getParameter("cursor") != null) {
                resourceValues.setCursorParam(req.getParameter("cursor"));
            }
            if (req.getParameter("filter") != null && req.getParameter("filterval") != null) {
                resourceValues.setFilterParams(req.getParameter("filter"), req.getParameter("filterval"));
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
