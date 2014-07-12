package se.mxt.code.radiocontrol.servlet;

import se.mxt.code.radiocontrol.ProgramSchedule;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by deejaybee on 7/12/14.
 */
public class RestEndpointDispatcher {
    private RestRequest request;
    private HttpServletResponse resp;

    public RestEndpointDispatcher(RestRequest request, HttpServletResponse resp) {
        this.request = request;
        this.resp = resp;
    }

    private void dispatchGET() throws ServletException, IOException {
        if (request.getResourceType().equals("schedule")) {
            Integer scheduleId = request.getId();

            if (scheduleId == null) {
                // Get a list of schedules
            } else {
                ProgramSchedule schedule = null;
                if (scheduleId == 0) {
                    // Mockup for test
                    MockupSchedule mockupSchedule = new MockupSchedule();
                    schedule = mockupSchedule.getSchedule();
                } else {

                }
                if (schedule == null) {
                    throw new ServletException("No schedule with ID '" + scheduleId + "' was found");
                }
                resp.setStatus(200);
                resp.setContentType("application/json");
                resp.getWriter().write(schedule.toJson());
            }

        } else {
            throw new ServletException("Invalid resource '" + request.getResourceType() + "'");
        }

    }

    public void dispatch() throws IOException {
        try {
            if (request.getAction().equals("GET")) {
                dispatchGET();
            }
        } catch (ServletException e) {
            resp.sendError(400, e.getMessage());
        }
    }

}
