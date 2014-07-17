package se.mxt.code.radiocontrol.mockups;

import se.mxt.code.radiocontrol.MPDPlaylistPlayer;
import se.mxt.code.radiocontrol.PlaylistPlayer;
import se.mxt.code.radiocontrol.ProgramChannel;

/**
 * Created by deejaybee on 7/15/14.
 */
public class MockupChannel {
    private ProgramChannel channel;

    public MockupChannel() {
        this.channel = new ProgramChannel("Creative Radio One", "http://aws01.mxt.se:8000/creativeradio?type=.mp3");
        this.channel.setImageURL("http://static.tumblr.com/bd185e7987329710f4534bc56b86ec2d/ag0q8zr/Zyan8hrrs/tumblr_static_u8yja1r6nqoogsc80cw80o0c.jpg");
    }

    public ProgramChannel getChannel() {
        return this.channel;
    }
}
