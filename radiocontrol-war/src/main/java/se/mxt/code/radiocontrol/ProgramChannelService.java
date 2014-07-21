package se.mxt.code.radiocontrol;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.Result;
import com.googlecode.objectify.cmd.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.mxt.code.radiocontrol.mockups.MockupChannel;

import java.util.LinkedList;
import java.util.List;

import static se.mxt.code.radiocontrol.OfyService.ofy;

/**
 * Created by deejaybee on 7/18/14.
 */
public class ProgramChannelService {
    private final static Logger LOG = LoggerFactory.getLogger(ProgramChannelService.class);
    private static ProgramChannelFinder channelFinderInstance = null;

    public static ProgramChannelFinder getChannelFinderInstance() {
        if (channelFinderInstance == null) {
            channelFinderInstance = new ProgramChannelFinder();
        }
        return channelFinderInstance;
    }

    public static ProgramChannelOwner getOwnerById(String ownerId) {
        ProgramChannelOwner owner = ofy().load().type(ProgramChannelOwner.class).id(ownerId).now();
        return owner;
    }

    public static ProgramChannel getChannelById(Long channelId) {
        ProgramChannel channel = ofy().load().type(ProgramChannel.class).id(channelId).now();
        return channel;
    }

    public static void saveChannel(ProgramChannel channel) {
        ofy().save().entity(channel);
    }

    public static void deleteChannelById(Long channelId) {
        if (channelId != 0) {
            ofy().delete().type(ProgramChannel.class).id(channelId).now();
        }
    }

    public static void saveOwner(ProgramChannelOwner owner) {
        ofy().save().entity(owner);
    }

    public static void deleteOwnerById(String ownerId) {
        ofy().delete().type(ProgramChannelOwner.class).id(ownerId).now();
    }

    public static List<ProgramChannel> getAllChannels(int pagelimit, String cursorString) {
        LOG.debug("Get all channels (" + pagelimit + ")");
        Query<ProgramChannel> query = ofy().load().type(ProgramChannel.class).limit(pagelimit);
        if (cursorString != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursorString));
        }
        QueryResultIterator<ProgramChannel> iterator = query.iterator();

        boolean hasMore = false;
        List<ProgramChannel> channelList = new LinkedList<>();
        // Include the mockup for fun...
        channelList.add(new MockupChannel().getChannel());
        while (iterator.hasNext()) {
            ProgramChannel channel = iterator.next();
            channelList.add(channel);
            hasMore = true;
        }

        if (hasMore) {
            // Handle pagination
        }
        return channelList;
    }

    public static List<ProgramChannel> getAllChannelsForOwner(String ownerId, int pagelimit, String cursorString) {
        LOG.debug("Get all channels for owner '" + ownerId + "' (" + pagelimit + ")");
        Key<ProgramChannelOwner> key = Key.create(ProgramChannelOwner.class, ownerId);
        Query<ProgramChannel> query = ofy().load().type(ProgramChannel.class)
                .filter("channelOwnerRef", key);
        if (cursorString != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursorString));
        }
        QueryResultIterator<ProgramChannel> iterator = query.iterator();

        boolean hasMore = false;
        List<ProgramChannel> channelList = new LinkedList<>();
        while (iterator.hasNext()) {
            ProgramChannel channel = iterator.next();
            channelList.add(channel);
            hasMore = true;
        }

        if (hasMore) {
            // Handle pagination
        }
        return channelList;

    }

    public static List<ProgramChannelOwner> getAllOwners(int pagelimit, String cursorString) {
        Query<ProgramChannelOwner> query = ofy().load().type(ProgramChannelOwner.class).limit(pagelimit);
        if (cursorString != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursorString));
        }
        QueryResultIterator<ProgramChannelOwner> iterator = query.iterator();

        boolean hasMore = false;
        List<ProgramChannelOwner> ownerList = new LinkedList<>();
        while (iterator.hasNext()) {
            ProgramChannelOwner owner = iterator.next();
            ownerList.add(owner);
            hasMore = true;
        }

        if (hasMore) {
            // Handle pagination
        }
        return ownerList;
    }
}
