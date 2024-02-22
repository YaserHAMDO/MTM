package com.example.mtm.model;

public class MediaReportModel {


    private final String date;
    private final String title;
    private final String program;
    private final String link;

    public MediaReportModel(String date, String title, String program, String link) {
        this.date = date;
        this.title = title;
        this.program = program;
        this.link = link;
    }


    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getProgram() {
        return program;
    }

    public String getLink() {
        return link;
    }
}
