package se.mxt.code.radiocontrol.api;

import javax.servlet.ServletException;

/**
 * Created by deejaybee on 7/17/14.
 */
public class RestException extends ServletException {
    private int errorcode;

    public RestException(int errorcode, String message) {
        super(message);
        this.errorcode = errorcode;
    }

    public int getErrorcode() {
        return errorcode;
    }
}
