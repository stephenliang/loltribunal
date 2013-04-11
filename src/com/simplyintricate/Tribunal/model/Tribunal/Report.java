package com.simplyintricate.Tribunal.model.Tribunal;

import java.util.Date;

public class Report {

    Date date;
    Date time;
    String summonerName;
    String sentTo;
    String message;
    String associationToOffender;
    String championName;
    boolean nameChange;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    public String getSentTo() {
        return sentTo;
    }

    public void setSentTo(String sentTo) {
        this.sentTo = sentTo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAssociationToOffender() {
        return associationToOffender;
    }

    public void setAssociationToOffender(String associationToOffender) {
        this.associationToOffender = associationToOffender;
    }

    public String getChampionName() {
        return championName;
    }

    public void setChampionName(String championName) {
        this.championName = championName;
    }

    public boolean isNameChange() {
        return nameChange;
    }

    public void setNameChange(boolean nameChange) {
        this.nameChange = nameChange;
    }

}
