package se.mxt.code.radiocontrol.servlet;

import se.mxt.code.radiocontrol.*;

/**
 * Created by deejaybee on 7/11/14.
 */
public class MockupSchedule {
    private ProgramSchedule schedule;

    public MockupSchedule() {
        PlaylistPlayer player = new MPDPlaylistPlayer("aws01.mxt.se");
        ProgramChannel channel = new ProgramChannel(player);
        schedule = new ProgramSchedule(channel);

        schedule.addBlock(new LiveBlock());
        schedule.addBlock(new MusicBlock(player));
        schedule.addBlock(new LiveBlock());
    }

    public ProgramSchedule getSchedule() {
        return schedule;
    }
}
