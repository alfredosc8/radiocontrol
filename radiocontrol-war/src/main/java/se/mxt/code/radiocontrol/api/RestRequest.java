package se.mxt.code.radiocontrol.api;

import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by deejaybee on 7/12/14.
 */
public class RestRequest {
    private Pattern patternAllResources = Pattern.compile("/(\\w+)");
    private Pattern patternResourceById = Pattern.compile("/(\\w+)/([0-9]*?)");
    private Long id;
    private String resourceType;
    private String action;
    private BufferedReader bodyReader;
    private String cursorParam;
    private String filterKey;
    private String filterValue;
    private static String[] validResourceTypes = { "schedule", "channel", "discover", "owner" };

    public RestRequest(String action, String pathInfo, BufferedReader bodyReader) throws ServletException {
        this.action = action;
        this.bodyReader = bodyReader;

        // regex parse pathInfo
        Matcher matcher;

        // Check for ID case first, since the All pattern would also match
        matcher = patternResourceById.matcher(pathInfo);
        if (matcher.matches()) {
            resourceType = matcher.group(1);
            id = Long.parseLong(matcher.group(2));

            if (!isValidResource(resourceType)) {
                throw new RestException(404, resourceType + " is not a valid resource");
            }
            return;
        }

        matcher = patternAllResources.matcher(pathInfo);
        if (matcher.matches()) {
            resourceType = matcher.group(1);
            if (!isValidResource(resourceType)) {
                throw new RestException(404, resourceType + " is not a valid resource");
            }
            return;
        }

        throw new ServletException("Invalid URI");
    }

    private boolean isValidResource(String resourceType) {
        for(String valid : validResourceTypes) {
            if (resourceType.equals(valid)) {
                return true;
            }
        }
        return false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResourceType() {
        return this.resourceType;
    }

    public String getAction() {
        return action;
    }

    public String readBody() throws IOException {
        BufferedReader reader = this.bodyReader;
        StringBuilder builder = new StringBuilder();
        String body = "";

        while((body = reader.readLine()) != null) {
            builder.append(body);
        }
        return builder.toString();
    }

    public void setCursorParam(String param) {
        this.cursorParam = param;
    }

    public String getCursorParam() {
        return cursorParam;
    }

    public void setFilterParams(String key, String value) {
        this.filterKey = key;
        this.filterValue = value;
    }

    public String getFilterKey() { return filterKey; };
    public String getFilterValue() { return filterValue; }
}
