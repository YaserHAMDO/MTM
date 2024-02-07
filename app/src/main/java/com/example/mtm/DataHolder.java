package com.example.mtm;

public class DataHolder {
    private static final DataHolder instance = new DataHolder();

    private MediaAgendaModel mediaAgendaModel;
    private NewspaperFirstPagesModel newspaperFirstPagesModel;
    private NewsPaperFullPagesModel newsPaperFullPagesModel;
    private MagazineFullPagesModel magazineFullPagesModel;
    private ColumnistsModel columnistsModel;
    private VisualMediaModel visualMediaModel;
    private SubMenuVisualMediaModel subMenuVisualMediaModel;
    private InternetModel internetModel;
    private InternetSubModel InternetSubModel;

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

    public NewspaperFirstPagesModel getNewspaperFirstPagesModel() {
        return newspaperFirstPagesModel;
    }

    public void setNewspaperFirstPagesModel(NewspaperFirstPagesModel newspaperFirstPagesModel) {
        this.newspaperFirstPagesModel = newspaperFirstPagesModel;
    }

    public NewsPaperFullPagesModel getNewsPaperFullPagesModel() {
        return newsPaperFullPagesModel;
    }

    public void setNewsPaperFullPagesModel(NewsPaperFullPagesModel newsPaperFullPagesModel) {
        this.newsPaperFullPagesModel = newsPaperFullPagesModel;
    }

    public MagazineFullPagesModel getMagazineFullPagesModel() {
        return magazineFullPagesModel;
    }

    public void setMagazineFullPagesModel(MagazineFullPagesModel magazineFullPagesModel) {
        this.magazineFullPagesModel = magazineFullPagesModel;
    }

    public ColumnistsModel getColumnistsModel() {
        return columnistsModel;
    }

    public void setColumnistsModel(ColumnistsModel columnistsModel) {
        this.columnistsModel = columnistsModel;
    }

    public VisualMediaModel getVisualMediaModel() {
        return visualMediaModel;
    }

    public void setVisualMediaModel(VisualMediaModel visualMediaModel) {
        this.visualMediaModel = visualMediaModel;
    }

    public SubMenuVisualMediaModel getSubMenuVisualMediaModel() {
        return subMenuVisualMediaModel;
    }

    public void setSubMenuVisualMediaModel(SubMenuVisualMediaModel subMenuVisualMediaModel) {
        this.subMenuVisualMediaModel = subMenuVisualMediaModel;
    }

    public InternetModel getInternetModel() {
        return internetModel;
    }

    public void setInternetModel(InternetModel internetModel) {
        this.internetModel = internetModel;
    }

    public com.example.mtm.InternetSubModel getInternetSubModel() {
        return InternetSubModel;
    }

    public void setInternetSubModel(com.example.mtm.InternetSubModel internetSubModel) {
        InternetSubModel = internetSubModel;
    }
}
