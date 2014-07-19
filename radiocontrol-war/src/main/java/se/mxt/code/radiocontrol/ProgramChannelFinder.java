package se.mxt.code.radiocontrol;

import com.googlecode.objectify.Key;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static se.mxt.code.radiocontrol.OfyService.ofy;

/**
 * Created by deejaybee on 7/18/14.
 */
public class ProgramChannelFinder {
    private static List<Key<ProgramChannel>> channelKeys = new ArrayList<>();

    public ProgramChannelFinder() {
        refresh();
    }

    private Key<ProgramChannel> poll() {
        if (!channelKeys.isEmpty()) {
            Key<ProgramChannel> key = channelKeys.get(0);
            channelKeys.remove(0);
            return key;
        }
        return null;
    }

    public ProgramChannel byRandom() {
        if (channelKeys.isEmpty()) {
            refresh();
        }
        if (!channelKeys.isEmpty()) {
            Collections.shuffle(channelKeys);
            ProgramChannel channel = ofy().load().key(poll()).now();
            return channel;
        }
        return null;
    }

    public ProgramChannel byChannelId(Long channelId) {
        ProgramChannel channel = ofy().load().type(ProgramChannel.class).id(channelId).now();
        return channel;
    }

    public void refresh() {
        channelKeys.clear();

        Iterable<Key<ProgramChannel>> allKeys = ofy().load().type(ProgramChannel.class).keys();
        for(Key<ProgramChannel> key : allKeys) {
            channelKeys.add(key);
        }
    }
}
