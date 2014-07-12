<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="se.mxt.code.radiocontrol.servlet.MockupSchedule" %>
<%@ page import="se.mxt.code.radiocontrol.ProgramSchedule" %>
<%@ page import="se.mxt.code.radiocontrol.ProgramScheduleRow" %>
<%@ page import="se.mxt.code.radiocontrol.ProgramBlock" %>
<%@ page import="se.mxt.code.radiocontrol.PlaylistPlayer" %>
<%@ page import="se.mxt.code.radiocontrol.ProgramChannel" %>
<%@ page import="java.util.List" %>
<%
    ProgramSchedule schedule = new MockupSchedule().getSchedule();
    ProgramChannel channel = schedule.getChannel();
%>
<!doctype html>
<html>
<head>
    <title>Schedule</title>
    <link type="text/css" rel="stylesheet" href="/stylesheets/schedule.css"/>
</head>

<body>
    <div id="main-container">
        <div id="channelinfo-container">
            <span>
            <p>Start time: <%=ProgramSchedule.formatDate(schedule.getStartTime())%></p>
            <p>Stop time: <%=ProgramSchedule.formatDate(schedule.getStopTime())%></p>
            </span>
        </div>

        <div id="playerinfo-container">
        <%
            PlaylistPlayer player = channel.getPlayer();
            String status = "Not connected to any playlist player";

            if (player != null && player.isConnected()) {
                if (player.isPlaying()) {
                    status = "Playlist player is playing '" + player.currentSong() + "'";
                } else {
                    status = "No music is playing";
                }
            }
        %>
        <p><%=status%></p>
        </div>

        <div id="schedule-container">
        <% for(ProgramScheduleRow row : schedule.getScheduleRows()) { %>
            <div id="block" class="block block-<%=row.getBlock().getType()%>">
              <table>
                <tr id="<%=row.getRowID()%>">
                  <td class="blocktime"><%=ProgramSchedule.format(row.getStartTime())%></td>
                  <td class="blocktype"><%=row.getBlock().getType()%></td>
                  <td class="blockdesc"><%=row.getBlock().getBlockInfo()%></td>
                </tr>
              </table>
            </div>
        <% } %>
        </div>
    </div>
</body>
</html>