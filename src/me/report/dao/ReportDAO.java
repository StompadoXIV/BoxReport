package me.report.dao;

import me.report.model.Report;

import java.util.ArrayList;
import java.util.List;

public class ReportDAO {

    private static List<Report> reports = new ArrayList<>();

    public static Report findReportByID(int id) {
        return reports.stream().filter(report -> report.getId() == id).findFirst().orElse(null);
    }

    public static List<Report> getReports() {
        return reports;
    }
}