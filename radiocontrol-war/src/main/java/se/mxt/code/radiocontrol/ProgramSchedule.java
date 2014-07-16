package se.mxt.code.radiocontrol;

import com.google.appengine.repackaged.org.joda.time.DateTime;
import com.google.appengine.repackaged.org.joda.time.format.DateTimeFormat;
import com.google.appengine.repackaged.org.joda.time.format.DateTimeFormatter;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.*;

import javax.json.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by deejaybee on 7/11/14.
 */
@Entity
public class ProgramSchedule {
    @Id Long id;
    DateTime scheduleStart;
    DateTime scheduleEnd;
    @Parent Ref<ProgramChannel> parentChannel;
    List<ProgramBlock> programBlocks = new ArrayList<ProgramBlock>();

    public static String format(DateTime time) {
        return time.toString("HH:mm:ss");
    }

    public static String formatDate(DateTime time) {
        return time.toString("YYYY-MM-dd HH:mm:ss");
    }

    private ProgramSchedule() {}

    public ProgramSchedule(DateTime start, DateTime end, ProgramChannel channel) {
        this.parentChannel = Ref.create(channel);
        this.scheduleStart = start;
        this.scheduleEnd = end;
    }

    public DateTime getStartTime() {
        return scheduleStart;
    }

    public DateTime getStopTime() {
        return scheduleEnd;
    }

    public ProgramChannel getChannel() {
        return parentChannel.get();
    }


    public void addBlock(ProgramBlock block) {
        block.setSeqNo(programBlocks.size());
        programBlocks.add(block);
    }

    public List<ProgramBlock> getBlocks() {
        return programBlocks;
    }

    public List<ProgramScheduleRow> getScheduleRows() {
        List<ProgramScheduleRow> scheduleRows = new ArrayList<ProgramScheduleRow>();

        scheduleRows.add(new ProgramScheduleRow(scheduleStart, scheduleStart, new FillBlock(FillBlock.START)));
        int startOffset = 0;
        for(ProgramBlock block : getBlocks()) {
            startOffset += block.getStartOffset();
            DateTime start = scheduleStart.plusSeconds(startOffset);
            DateTime end = start.plusSeconds(block.getDuration());
            ProgramScheduleRow row = new ProgramScheduleRow(start, end, block);
            startOffset += block.getDuration();
            scheduleRows.add(row);
        }
        DateTime fillStart = scheduleStart.plusSeconds(startOffset);
        scheduleRows.add(new ProgramScheduleRow(fillStart, scheduleEnd, new FillBlock(FillBlock.FILL)));
        scheduleRows.add(new ProgramScheduleRow(scheduleEnd, scheduleEnd, new FillBlock(FillBlock.END)));
        return scheduleRows;
    }

    public JsonObject asJsonObject() {
        JsonArrayBuilder jsonRowsBuilder = Json.createArrayBuilder();
        for(ProgramScheduleRow row : getScheduleRows()) {
            jsonRowsBuilder.add(row.asJsonObject());
        }

        return Json.createObjectBuilder()
            .add("channel", parentChannel.get().asJsonObject())
            .add("rows", jsonRowsBuilder.build())
            .add("start", ProgramSchedule.formatDate(scheduleStart))
            .add("end", ProgramSchedule.formatDate(scheduleEnd)).build();

    }

    public String toJson() {
        return asJsonObject().toString();
    }
}
