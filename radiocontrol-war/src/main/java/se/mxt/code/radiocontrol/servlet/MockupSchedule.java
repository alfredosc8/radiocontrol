package se.mxt.code.radiocontrol.servlet;

import com.google.appengine.repackaged.org.joda.time.DateTime;
import se.mxt.code.radiocontrol.*;

/**
 * Created by deejaybee on 7/11/14.
 */
public class MockupSchedule {
    private ProgramSchedule schedule;

    public MockupSchedule() {
        PlaylistPlayer player = new MPDPlaylistPlayer("aws01.mxt.se");
        ProgramChannel channel = new ProgramChannel(player);
        DateTime now = DateTime.now();
        DateTime tomorrow = DateTime.now().plusDays(1);
        DateTime start = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), 8, 0);
        DateTime end = new DateTime(tomorrow.getYear(), tomorrow.getMonthOfYear(), tomorrow.getDayOfMonth(), 1, 0);
        schedule = new ProgramSchedule(start, end, channel);

        schedule.addBlock(new LiveBlock(0, "A&B Creative Duo live set"));
        MusicBlock syntheticBlock = new MusicBlock(player, 0, "Synthetic House marathon");
        syntheticBlock.setPlaylistName("synthetic");
        schedule.addBlock(syntheticBlock);
        schedule.addBlock(new LiveBlock(0, 2*3600, "Secret guest DJ"));
        schedule.addBlock(new MusicBlock(player, 0, 4*3600, "Electro sessions"));
        schedule.addBlock(new MusicBlock(player, 0, 3*3600, "House session"));
        schedule.addBlock(new LiveBlock(0, 2*3600, "Stripped with DJ blah"));
        schedule.addBlock(new MusicBlock(player, 0, 3*3600, "Night music"));
    }

    public ProgramSchedule getSchedule() {
        return schedule;
    }
}
