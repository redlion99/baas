package com.rapple.baas.common.dto;

import java.util.Date;

/**
 * Created by libin on 14-11-25.
 */
public class InstantMessage {
    private User from;
    private User to;
    private String content;
    private Date sendTime=new Date();

    public InstantMessage() {
    }

    public InstantMessage(User from, String content) {
        this.from = from;
        this.content = content;
    }

    public InstantMessage(User from, User to, String content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InstantMessage that = (InstantMessage) o;

        if (from != null ? !from.equals(that.from) : that.from != null) return false;
        if (sendTime != null ? !sendTime.equals(that.sendTime) : that.sendTime != null) return false;
        if (to != null ? !to.equals(that.to) : that.to != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = from != null ? from.hashCode() : 0;
        result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result + (sendTime != null ? sendTime.hashCode() : 0);
        return result;
    }
}
