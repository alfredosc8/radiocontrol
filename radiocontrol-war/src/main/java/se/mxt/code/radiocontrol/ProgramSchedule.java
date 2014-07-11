package se.mxt.code.radiocontrol;

import com.google.appengine.repackaged.org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deejaybee on 7/11/14.
 */
public class ProgramSchedule {
    private DateTime scheduleStart;
    private DateTime scheduleEnd;
    private ProgramChannel channel;
    private List<ProgramBlock> programBlocks;

    public ProgramSchedule(ProgramChannel channel) {
        programBlocks = new ArrayList<ProgramBlock>();
        this.channel = channel;
    }

    public ProgramChannel getChannel() {
        return channel;
    }

    public void addBlock(ProgramBlock block) {
        programBlocks.add(block);
    }

    public List<ProgramBlock> getBlocks() {
        return programBlocks;
    }
}
