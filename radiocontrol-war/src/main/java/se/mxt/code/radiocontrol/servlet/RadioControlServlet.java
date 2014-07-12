package se.mxt.code.radiocontrol.servlet;

import com.google.gson.Gson;
import se.mxt.code.radiocontrol.ProgramSchedule;

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
    private class RestRequest {
        private Pattern patternAllResources = Pattern.compile("/(\\w+)");
        private Pattern patternResourceById = Pattern.compile("/(\\w+)/([0-9]*)");
        private Integer id;
        private String resourceType;

        public RestRequest(String pathInfo) throws ServletException {
            // regex parse pathInfo
            Matcher matcher;

            // Check for ID case first, since the All pattern would also match
            matcher = patternResourceById.matcher(pathInfo);
            if (matcher.find()) {
                resourceType = matcher.group(1);
                id = Integer.parseInt(matcher.group(2));
                return;
            }

            matcher = patternAllResources.matcher(pathInfo);
            if (matcher.find()) {
                resourceType = matcher.group(1);
                return;
            }

            throw new ServletException("Invalid URI");
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getResourceType() {
            return this.resourceType;
        }
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            RestRequest resourceValues = new RestRequest(req.getPathInfo());
            if (resourceValues.getResourceType().equals("schedule")) {
                // Mockup for test
                MockupSchedule mockupSchedule = new MockupSchedule();
                ProgramSchedule schedule = mockupSchedule.getSchedule();

                resp.setStatus(200);
                resp.setContentType("application/json");
                resp.getWriter().write(schedule.toJson());
            } else {
                throw new ServletException("Invalid resource '" + resourceValues.getResourceType() + "'");
            }
        } catch (ServletException e) {
            resp.setStatus(400);
            resp.resetBuffer();
            e.printStackTrace();
        }
    }
}
