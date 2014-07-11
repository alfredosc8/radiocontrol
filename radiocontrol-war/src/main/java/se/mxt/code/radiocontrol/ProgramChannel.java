package se.mxt.code.radiocontrol;

import java.util.Collection;

/**
 * Created by deejaybee on 7/11/14.
 */
public class ProgramChannel {
    private String channelTitle;
    private Collection<ProgramSchedule> schedules;
    private PlaylistPlayer player;

    public ProgramChannel(PlaylistPlayer player) {
        this.player = player;
    }

    public PlaylistPlayer getPlayer() {
        return player;
    }

}
