package com.simplyintricate.Tribunal.model.Tribunal;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stephen
 * Date: 4/10/13
 * Time: 10:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameDetail {
    Date gameCreationTime;
    String gameMode;
    GameType gameType;
    boolean isPreMade;
    List<ChatMessage> chatLog;
    int offenderId;
    List<Player> players;
    List<Report> reports;
    String mostCommonReportReason;
    int alliedReportCount;
    int enemyReportCount;
    int platformGameId;
    String version;
    HashMap<String, String> translatedReportedBy;
    HashMap<String, String> translatedReportReasons;
    String gameModeRaw;
    int caseTotalReports;
    String offender;
}
