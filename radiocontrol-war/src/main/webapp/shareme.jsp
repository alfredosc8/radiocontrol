<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="se.mxt.code.radiocontrol.ProgramChannel" %>
<%@ page import="se.mxt.code.radiocontrol.ProgramChannelFinder" %>
<%@ page import="se.mxt.code.radiocontrol.ProgramChannelService" %>
<%@ page import="se.mxt.code.radiocontrol.mockups.MockupChannel" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
<%
        Long channelId;
        if (request.getParameter("channelId") == null) {
            channelId = new Long(0);
        } else {
            channelId = Long.parseLong(request.getParameter("channelId"));
        }
        ProgramChannel channel;
        if (channelId != 0) {
            channel = ProgramChannelService.getChannelFinderInstance().byChannelId(channelId);
        } else {
            channel = new MockupChannel().getChannel();
        }
%>
        <script type="text/javascript">
           function radioplayer(id) {
              window.location = '/#/radioplayer/' + id;
           }
        </script>
        <title><%=channel.getChannelTitle()%></title>
        <meta property="og:image" content="<%=channel.getImageURL()%>"/>
        <meta property="og:title" content="<%=channel.getChannelTitle()%>"/>
        <meta property="og:site_name" content="Vintage Radio by Mixit"/>
        <meta property="og:type" content="website"/>
        <meta property="og:description" content="Listen to this radio channel and discover more in the ether of internet radios!"/>
    </head>
    <body onload="radioplayer(<%=channelId%>)">
    </body>
</html>