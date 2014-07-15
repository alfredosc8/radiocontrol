package se.mxt.code.radiocontrol;

/**
 * Created by deejaybee on 7/11/14.
 */
public class FillBlock implements ProgramBlock {
    public static String FILL = "fill";
    public static String START = "start";
    public static String END = "end";

    private String type = FillBlock.FILL; // start, end, fill
    private int seqNo;

    public FillBlock(String type) {
        this.type = type;
    }

    @Override
    public void setBlockInfo(String info) {

    }

    @Override
    public String getBlockInfo() {
        return "";
    }

    @Override
    public int getStartOffset() {
        return 0;
    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    @Override
    public int getSeqNo() {
        return this.seqNo;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void take() { }

    @Override
    public void untake() { }

}
