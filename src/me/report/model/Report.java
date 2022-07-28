package me.report.model;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Report {

    private String author, reported, date, reason;
    private int id;

    public Report(String author, String reported, String reason, int id) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm");

        this.author = author;
        this.reported = reported;
        this.date = sdf.format(Calendar.getInstance().getTime());
        this.reason = reason;
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public String getReported() {
        return reported;
    }

    public String getDate() {
        return date;
    }

    public String getReason() {
        return reason;
    }

    public int getId() {
        return id;
    }

    public Player getPlayerReported() {
        return Bukkit.getPlayer(reported);
    }
}