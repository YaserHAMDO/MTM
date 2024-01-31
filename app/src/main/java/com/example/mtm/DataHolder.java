package com.example.mtm;

public class DataHolder {
    private static final DataHolder instance = new DataHolder();
    private MediaAgendaModel mediaAgendaModel;

    private DataHolder() {}

    public static DataHolder getInstance() {
        return instance;
    }

    public MediaAgendaModel getMediaAgendaModel() {
        return mediaAgendaModel;
    }

    public void setMediaAgendaModel(MediaAgendaModel mediaAgendaModel) {
        this.mediaAgendaModel = mediaAgendaModel;
    }
}
