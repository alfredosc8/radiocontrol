package se.mxt.code.radiocontrol;

/**
 * Created by deejaybee on 7/11/14.
 */
public class FillBlock implements ProgramBlock {

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
        return "fill";
    }

}
