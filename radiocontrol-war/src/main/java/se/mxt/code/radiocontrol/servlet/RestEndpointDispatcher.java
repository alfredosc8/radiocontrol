package se.mxt.code.radiocontrol.servlet;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Result;
import se.mxt.code.radiocontrol.ProgramChannel;
import se.mxt.code.radiocontrol.ProgramSchedule;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Logger;

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
            Long scheduleId = request.getId();

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
        } else if(request.getResourceType().equals("channel")) {
            Long channelId = request.getId();

            if (channelId == null) {
                // Get a list of channels)
            } else {
                ProgramChannel channel = null;
                if (channelId == 0) {
                    // Mockup for test
                    MockupChannel mockupChannel = new MockupChannel();
                    channel = mockupChannel.getChannel();
                } else {
                    channel = ofy().load().type(ProgramChannel.class).id(channelId).now();
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

    public void dispatchPOST() throws ServletException, IOException {

        String body = request.readBody();

        if (request.getResourceType().equals("channel")) {
//            System.out.println("BODY: " + body);
            ProgramChannel channel = ProgramChannel.fromJson(body);
            Result<Key<ProgramChannel>> result = ofy().save().entity(channel);
            result.now();

            resp.setStatus(200);
            resp.setContentType("application/json");
            resp.getWriter().write(channel.toJson());
        }
    }

    public void dispatch() throws IOException {
        try {
            System.out.println(request.getAction());
            if (request.getAction().equals("GET")) {
                dispatchGET();
            } else if(request.getAction().equals("POST")) {
                dispatchPOST();
            }
        } catch (ServletException e) {
            resp.sendError(400, e.getMessage());
        }
    }

}
