package se.mxt.code.radiocontrol;

/**
 * Created by deejaybee on 7/18/14.
 */
public class ProgramChannelService {
    private static ProgramChannelFinder channelFinderInstance = null;

    public static ProgramChannelFinder getChannelFinderInstance() {
        if (channelFinderInstance == null) {
            channelFinderInstance = new ProgramChannelFinder();
        }
        return channelFinderInstance;
    }
}
