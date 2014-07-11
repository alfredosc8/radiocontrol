package se.mxt.code.radiocontrol;

/**
 * Created by deejaybee on 7/11/14.
 */
public interface ProgramBlock {
    public static int DEFAULT_DURATION = 3600;

    public int getStartOffset();
    public int getDuration();
    public String getType();
    public void setBlockInfo(String info);
    public String getBlockInfo();
}
