package se.mxt.code.radiocontrol.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.mxt.code.radiocontrol.*;
import se.mxt.code.radiocontrol.mockups.MockupChannel;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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
            List<ProgramChannel> channels;
            if (request.getFilterKey() != null && request.getFilterKey().equals("owner")) {
                // Get a list of channels by owner
                channels = ProgramChannelService.getAllChannelsForOwner(request.getFilterValue(), 1000, request.getCursorParam());
            } else {
                // Get a list of all channels)
                channels = ProgramChannelService.getAllChannels(1000, request.getCursorParam());
            }
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            for (ProgramChannel channel : channels) {
                jsonArrayBuilder.add(channel.asJsonObject());
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
                channel = ProgramChannelService.getChannelById(channelId);
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

    private void handleOwner() throws ServletException, IOException {
        List<ProgramChannelOwner> owners = ProgramChannelService.getAllOwners(1000, request.getCursorParam());
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (ProgramChannelOwner owner : owners) {
            jsonArrayBuilder.add(owner.asJsonObject());
        }
        JsonObject jsonResponse = Json.createObjectBuilder().add("owners", jsonArrayBuilder.build()).build();
        resp.setStatus(200);
        resp.setContentType("application/json");
        resp.getWriter().write(jsonResponse.toString());
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
            ProgramChannelService.saveChannel(channel);
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
                channel = ProgramChannelService.getChannelById(channelId);
                if (channel == null) {
                    throw new RestException(404, "No channel with ID '" + channelId + "' was found");
                }
                channel.fromJson(body);
                ProgramChannelService.saveChannel(channel);
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
            ProgramChannelService.deleteChannelById(request.getId());
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
