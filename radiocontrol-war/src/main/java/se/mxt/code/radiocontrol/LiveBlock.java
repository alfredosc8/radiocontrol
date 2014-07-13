package se.mxt.code.radiocontrol;

/**
 * Created by deejaybee on 7/11/14.
 */
public class LiveBlock implements ProgramBlock {
    private int startOffset = 0;
    private int blockDuration = ProgramBlock.DEFAULT_DURATION;
    private String blockInfo;
    private int seqNo;

    public LiveBlock(int startOffset) {
        this.startOffset = startOffset;
    }

    public LiveBlock(int startOffset, String info) {
        this.startOffset = startOffset;
        this.blockInfo = info;
    }

    public LiveBlock(int startOffset, int blockDuration) {
        this.startOffset = startOffset;
        this.blockDuration = blockDuration;
    }

    public LiveBlock(int startOffset, int blockDuration, String info) {
        this.startOffset = startOffset;
        this.blockDuration = blockDuration;
        this.blockInfo = info;
    }

    @Override
    public void setBlockInfo(String info) {
        this.blockInfo = info;
    }

    @Override
    public String getBlockInfo() {
        return blockInfo;
    }

    @Override
    public int getStartOffset() {
        return startOffset;
    }

    @Override
    public int getDuration() {
        return blockDuration;
    }

    @Override
    public String getType() {
        return "live";
    }

    @Override
    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    @Override
    public int getSeqNo() {
        return this.seqNo;
    }
}
