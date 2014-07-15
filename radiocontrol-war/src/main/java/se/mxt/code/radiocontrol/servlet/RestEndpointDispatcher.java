package se.mxt.code.radiocontrol.servlet;

import se.mxt.code.radiocontrol.ProgramChannel;
import se.mxt.code.radiocontrol.ProgramSchedule;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static se.mxt.code.radiocontrol.OfyService.ofy;

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
                    schedule = ofy().load().type(ProgramSchedule.class).id(scheduleId).now();
                }
                if (schedule == null) {
                    throw new ServletException("No schedule with ID '" + scheduleId + "' was found");
                }
                resp.setStatus(200);
                resp.setContentType("application/json");
                resp.getWriter().write(schedule.toJson());
            }
        } else if(request.getResourceType().equals("channel")) {
            Integer channelId = request.getId();

            if (channelId == null) {
                // Get a list of channels)
            } else {
                ProgramChannel channel = null;
                if (channelId == 0) {
                    // Mockup for test
                    MockupChannel mockupChannel = new MockupChannel();
                    channel = mockupChannel.getChannel();
                } else {

                }
                if (channel == null) {
                    throw new ServletException("No channel with ID '" + channelId + "' was found");
                }
                resp.setStatus(200);
                resp.setContentType("application/json");
                resp.getWriter().write(channel.toJson());
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
