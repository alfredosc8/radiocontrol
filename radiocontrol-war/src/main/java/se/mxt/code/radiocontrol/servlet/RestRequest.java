package se.mxt.code.radiocontrol.servlet;

import javax.servlet.ServletException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by deejaybee on 7/12/14.
 */
public class RestRequest {
    private Pattern patternAllResources = Pattern.compile("/(\\w+)");
    private Pattern patternResourceById = Pattern.compile("/(\\w+)/([0-9]*)");
    private Integer id;
    private String resourceType;
    private String action;

    public RestRequest(String action, String pathInfo) throws ServletException {
        // regex parse pathInfo
        Matcher matcher;
        this.action = action;

        // Check for ID case first, since the All pattern would also match
        matcher = patternResourceById.matcher(pathInfo);
        if (matcher.find()) {
            resourceType = matcher.group(1);
            id = Integer.parseInt(matcher.group(2));
            return;
        }

        matcher = patternAllResources.matcher(pathInfo);
        if (matcher.find()) {
            resourceType = matcher.group(1);
            return;
        }

        throw new ServletException("Invalid URI");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResourceType() {
        return this.resourceType;
    }

    public String getAction() {
        return action;
    }
}
