package se.mxt.code.radiocontrol.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by deejaybee on 7/12/14.
 */
public class RestRequest {
    private Pattern patternAllResources = Pattern.compile("/(\\w+)");
    private Pattern patternResourceById = Pattern.compile("/(\\w+)/([0-9]*)");
    private Long id;
    private String resourceType;
    private String action;
    private BufferedReader bodyReader;
    private String cursorParam;

    public RestRequest(String action, String pathInfo, BufferedReader bodyReader) throws ServletException {
        // regex parse pathInfo
        Matcher matcher;
        this.action = action;
        this.bodyReader = bodyReader;

        // Check for ID case first, since the All pattern would also match
        matcher = patternResourceById.matcher(pathInfo);
        if (matcher.find()) {
            resourceType = matcher.group(1);
            id = Long.parseLong(matcher.group(2));
            return;
        }

        matcher = patternAllResources.matcher(pathInfo);
        if (matcher.find()) {
            resourceType = matcher.group(1);
            return;
        }

        throw new ServletException("Invalid URI");
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

}
