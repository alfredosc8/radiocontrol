package se.mxt.code.radiocontrol;

/**
 * Created by deejaybee on 7/11/14.
 */
public class MusicBlock implements ProgramBlock {
    private PlaylistPlayer playlistPlayer;

    public MusicBlock(PlaylistPlayer player) {
        this.playlistPlayer = player;
    }

    @Override
    public String getType() {
        return "music";
    }
}
