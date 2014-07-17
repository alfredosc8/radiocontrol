package se.mxt.code.radiocontrol.test.unit;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import se.mxt.code.radiocontrol.servlet.RestRequest;

import javax.servlet.ServletException;

/**
 * Created by deejaybee on 7/17/14.
 */
public class RestRequestTest {

    @Test
    public void parseRequestString() {
        try {
            RestRequest resource = new RestRequest("GET", "/channel/1234567", null);
            Assert.assertEquals(new Long(1234567), resource.getId());
            Assert.assertEquals("channel", resource.getResourceType());
            Assert.assertEquals("GET", resource.getAction());
        } catch (ServletException e) {

        }
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void parseInvalidRequestString() {
        try {
            RestRequest resource = new RestRequest("GET", "/channel/stuff/1234567", null);
            exception.expect(ServletException.class);
            exception.expectMessage("Invalid URI");
        } catch (ServletException e) {

        }
    }
}
