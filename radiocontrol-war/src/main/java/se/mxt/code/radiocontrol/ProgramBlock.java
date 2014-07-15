package se.mxt.code.radiocontrol;

/**
 * Created by deejaybee on 7/11/14.
 */
public interface ProgramBlock {
    public static int DEFAULT_DURATION = 3600;

    public void take();
    public void untake();

    public int getStartOffset();
    public int getDuration();
    public String getType();
    public void setBlockInfo(String info);
    public String getBlockInfo();
    public void setSeqNo(int seqNo);
    public int getSeqNo();
    public boolean isActive();
}
