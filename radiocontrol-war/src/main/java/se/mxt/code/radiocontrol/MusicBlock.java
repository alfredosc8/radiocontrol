package se.mxt.code.radiocontrol;

/**
 * Created by deejaybee on 7/11/14.
 */
public class MusicBlock implements ProgramBlock {
    private PlaylistPlayer playlistPlayer;
    private int startOffset = 0;
    private int blockDuration = ProgramBlock.DEFAULT_DURATION;
    private String blockInfo;
    private String playlistName = "NO PLAYLIST SPECIFIED";
    private int seqNo;

    public MusicBlock(PlaylistPlayer player, int startOffset) {
        this.startOffset = startOffset;
        this.playlistPlayer = player;
    }

    public MusicBlock(PlaylistPlayer player, int startOffset, String blockInfo) {
        this.startOffset = startOffset;
        this.playlistPlayer = player;
        this.blockInfo = blockInfo;
    }

    public MusicBlock(PlaylistPlayer player, int startOffset, int blockDuration) {
        this.startOffset = startOffset;
        this.blockDuration = blockDuration;
        this.playlistPlayer = player;
    }

    public MusicBlock(PlaylistPlayer player, int startOffset, int blockDuration, String blockInfo) {
        this.startOffset = startOffset;
        this.blockDuration = blockDuration;
        this.playlistPlayer = player;
        this.blockInfo = blockInfo;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    @Override
    public void setBlockInfo(String info) {
        this.blockInfo = info;
    }

    @Override
    public String getBlockInfo() {
        return blockInfo + " ("+playlistName+")";
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
        return "music";
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
