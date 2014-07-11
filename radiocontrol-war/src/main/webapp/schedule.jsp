<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="se.mxt.code.radiocontrol.servlet.MockupSchedule" %>
<%@ page import="se.mxt.code.radiocontrol.ProgramSchedule" %>
<%@ page import="se.mxt.code.radiocontrol.ProgramBlock" %>
<%@ page import="se.mxt.code.radiocontrol.PlaylistPlayer" %>
<%@ page import="se.mxt.code.radiocontrol.ProgramChannel" %>
<%@ page import="java.util.List" %>
<%
    ProgramSchedule schedule = new MockupSchedule().getSchedule();
    ProgramChannel channel = schedule.getChannel();
%>
<html>
<head>
    <title>Schedule</title>
</head>

<body>
    <div id="channelinfo">
    </div>

    <div id="playerinfo">
    <%
        PlaylistPlayer player = channel.getPlayer();
        String status = "No music is playing";

        if (player.isPlaying()) {
            status = "Music player is playing " + player.currentSong();
        }
    %>
    <p><%=status%></p>
    </div>

    <div id="schedule">
    <% for(ProgramBlock block : schedule.getBlocks()) { %>
        <div id="block" class="block-<%=block.getType()%>">
        </div>
    <% } %>
    </div>
</body>
</html>