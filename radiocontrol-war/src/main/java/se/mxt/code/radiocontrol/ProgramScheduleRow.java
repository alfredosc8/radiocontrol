package se.mxt.code.radiocontrol;

import com.google.appengine.repackaged.org.joda.time.DateTime;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * Created by deejaybee on 7/11/14.
 */
public class ProgramScheduleRow {
    private ProgramBlock block;
    private DateTime absStart;
    private DateTime absEnd;

    public ProgramScheduleRow(DateTime absStart, DateTime absEnd, ProgramBlock block) {
        this.block = block;
        this.absStart = absStart;
        this.absEnd = absEnd;
    }

    public DateTime getStartTime() {
        return absStart;
    }

    public DateTime getStopTime() {
        return absEnd;
    }

    public ProgramBlock getBlock() {
        return block;
    }

    public String getRowID() {
        return block.getType() + "_" + absStart.toString("HHmmss");
    }

    public JsonObject asJsonObject() {
        return Json.createObjectBuilder()
                .add("seqno", block.getSeqNo())
                .add("start", ProgramSchedule.format(absStart))
                .add("type", block.getType())
                .add("description", block.getBlockInfo()).build();
    }
}
