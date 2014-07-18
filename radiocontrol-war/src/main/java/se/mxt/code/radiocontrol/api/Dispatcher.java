package se.mxt.code.radiocontrol.api;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Result;
import com.googlecode.objectify.cmd.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.mxt.code.radiocontrol.ProgramChannel;
import se.mxt.code.radiocontrol.ProgramChannelFinder;
import se.mxt.code.radiocontrol.ProgramChannelService;
import se.mxt.code.radiocontrol.ProgramSchedule;
import se.mxt.code.radiocontrol.mockups.MockupChannel;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static se.mxt.code.radiocontrol.OfyService.ofy;

/**
 * Created by deejaybee on 7/12/14.
 */
public class Dispatcher {
    private final static Logger LOG = LoggerFactory.getLogger(Dispatcher.class);
    private RestRequest request;
    private HttpServletResponse resp;
    int page;

    public Dispatcher(RestRequest request, HttpServletResponse resp) {
        this.request = request;
        this.resp = resp;
    }

    private void handleSchedule() throws ServletException, IOException {
        Long scheduleId = request.getId();

        if (scheduleId == null) {
            // Get a list of schedules
        } else {
            ProgramSchedule schedule = null;
            if (scheduleId == 0) {
                // Mockup for test
                // MockupSchedule mockupSchedule = new MockupSchedule();
                // schedule = mockupSchedule.getSchedule();
            } else {

            }
            if (schedule == null) {
                throw new RestException(404, "No schedule with ID '" + scheduleId + "' was found");
            }
            resp.setStatus(200);
            resp.setContentType("application/json");
            resp.getWriter().write(schedule.toJson());
        }
    }

    private void handleChannel() throws ServletException, IOException {
        Long channelId = request.getId();

        if (channelId == null) {
            // Get a list of channels)
            Query<ProgramChannel> query = ofy().load().type(ProgramChannel.class).limit(1000);
            if (request.getCursorParam() != null) {
                query = query.startAt(Cursor.fromWebSafeString(request.getCursorParam()));
            }
            boolean cont = false;
            QueryResultIterator<ProgramChannel> iterator = query.iterator();
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            // Include the mockup for fun...
            jsonArrayBuilder.add(new MockupChannel().getChannel().asJsonObject());
            while (iterator.hasNext()) {
                ProgramChannel channel = iterator.next();
                jsonArrayBuilder.add(channel.asJsonObject());
                cont = true;
            }
            if (cont) {
                // Add a link to next page in response
            }
            JsonObject jsonResponse = Json.createObjectBuilder().add("channels", jsonArrayBuilder.build()).build();
            resp.setStatus(200);
            resp.setContentType("application/json");
            resp.getWriter().write(jsonResponse.toString());
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
                throw new RestException(404, "No channel with ID '" + channelId + "' was found");
            }
            resp.setStatus(200);
            resp.setContentType("application/json");
            resp.getWriter().write(channel.toJson());
        }
    }

    private void handleDiscover() throws ServletException, IOException {
        ProgramChannelFinder channelFinder = ProgramChannelService.getChannelFinderInstance();
        ProgramChannel channel = channelFinder.byRandom();
        if (channel == null) {
            throw new RestException(404, "Not able to discover a new channel by random");
        }
        resp.setStatus(200);
        resp.setContentType("application/json");
        resp.getWriter().write(channel.toJson());
    }

    private void dispatchGET() throws ServletException, IOException {
        if (request.getResourceType().equals("schedule")) {
            handleSchedule();
        } else if(request.getResourceType().equals("channel")) {
            handleChannel();
        } else if(request.getResourceType().equals("discover")) {
            handleDiscover();
        } else {
            throw new ServletException("Invalid resource '" + request.getResourceType() + "'");
        }

    }

    public void dispatchPOST() throws ServletException, IOException {

        String body = request.readBody();

        if (request.getResourceType().equals("channel")) {
            LOG.debug("BODY: " + body);
            ProgramChannel channel = ProgramChannel.buildFromJson(body);
            Result<Key<ProgramChannel>> result = ofy().save().entity(channel);
            result.now();

            resp.setStatus(200);
            resp.setContentType("application/json");
            resp.getWriter().write(channel.toJson());
        }
    }

    public void dispatchPUT() throws ServletException, IOException {
        String body = request.readBody();

        if (request.getResourceType().equals("channel")) {
            LOG.debug("BODY: " + body);
            Long channelId = request.getId();

            if (channelId != null) {
                ProgramChannel channel = null;
                if (channelId == 0) {
                    throw new RestException(403, "Channel ID 0 cannot be updated");
                }
                channel = ofy().load().type(ProgramChannel.class).id(channelId).now();
                if (channel == null) {
                    throw new RestException(404, "No channel with ID '" + channelId + "' was found");
                }
                channel.fromJson(body);
                ofy().save().entity(channel).now();
                resp.setStatus(200);
                resp.setContentType("application/json");
                resp.getWriter().write(channel.toJson());
            }

        } else {
            throw new ServletException("Invalid resource '" + request.getResourceType() + "'");
        }

    }

    public void dispatchDELETE() {
        if (request.getResourceType().equals("channel")) {
            Long channelId = request.getId();
            if (channelId != 0) {
                ofy().delete().type(ProgramChannel.class).id(channelId).now();
            }
            resp.setStatus(200);
        }
    }

    public void dispatch() throws IOException {
        try {
            if (request.getAction().equals("GET")) {
                dispatchGET();
            } else if (request.getAction().equals("PUT")) {
                dispatchPUT();
            } else if (request.getAction().equals("POST")) {
                dispatchPOST();
            } else if (request.getAction().equals("DELETE")) {
                dispatchDELETE();
            }
        } catch (RestException e) {
            resp.sendError(e.getErrorcode(), e.getMessage());
        } catch (ServletException e) {
            resp.sendError(400, e.getMessage());
        }
    }

}
