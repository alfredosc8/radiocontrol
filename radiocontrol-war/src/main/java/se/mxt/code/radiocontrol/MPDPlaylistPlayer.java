package se.mxt.code.radiocontrol;

import org.bff.javampd.MPD;
import org.bff.javampd.Player;
import org.bff.javampd.Status;
import org.bff.javampd.exception.MPDException;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by deejaybee on 7/11/14.
 */
public class MPDPlaylistPlayer implements PlaylistPlayer {
    private MPD mpdServer;
    private Player mpdPlayer;

    public MPDPlaylistPlayer(String host) {
        try {
            mpdServer = new MPD.Builder().server(host).port(6600).build();
            mpdPlayer = mpdServer.getPlayer();
        } catch (IOException ex) {
            Logger.getLogger(MPDPlaylistPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MPDException ex) {
            Logger.getLogger(MPDPlaylistPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void loadPlaylist(String playlist) {

    }

    @Override
    public boolean isPlaying() {
        try {
            Player.Status status = mpdPlayer.getStatus();
            if (status == Player.Status.STATUS_PLAYING) {
                return true;
            }
        } catch (MPDException ex) {
            Logger.getLogger(MPDPlaylistPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public void play() {

    }

    @Override
    public void pause() {

    }

    @Override
    public String currentSong() {
        try {
            return mpdPlayer.getCurrentSong().getName();
        } catch (MPDException ex) {
            Logger.getLogger(MPDPlaylistPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
