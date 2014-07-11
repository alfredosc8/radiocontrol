package se.mxt.code.radiocontrol;

/**
 * Created by deejaybee on 7/11/14.
 */
public interface PlaylistPlayer {
    public void loadPlaylist(String playlist);
    public void play();
    public void pause();
    public boolean isPlaying();
    public String currentSong();

}
