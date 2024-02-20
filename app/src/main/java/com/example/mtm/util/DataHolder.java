package com.example.mtm.util;

import com.example.mtm.response.ColumnistsResponse;
import com.example.mtm.response.InternetResponse;
import com.example.mtm.response.InternetSubResponse;
import com.example.mtm.response.MagazineFullPagesResponse;
import com.example.mtm.response.MediaAgendaResponse;
import com.example.mtm.response.MenuListResponse;
import com.example.mtm.response.NewsPaperFullPagesResponse;
import com.example.mtm.response.NewspaperFirstPagesResponse;
import com.example.mtm.response.NotificationsResponse;
import com.example.mtm.response.PrintedMediaSubResponse;
import com.example.mtm.response.SubMenuVisualMediaResponse;
import com.example.mtm.response.SummaryListResponse;
import com.example.mtm.response.VisualMediaResponse;

public class DataHolder {
    private static final DataHolder instance = new DataHolder();

    private MediaAgendaResponse mediaAgendaModel;
    private NewspaperFirstPagesResponse newspaperFirstPagesModel;
    private NewsPaperFullPagesResponse newsPaperFullPagesModel;
    private MagazineFullPagesResponse magazineFullPagesModel;
    private ColumnistsResponse columnistsModel;
    private VisualMediaResponse visualMediaModel;
    private SubMenuVisualMediaResponse subMenuVisualMediaModel;
    private InternetResponse internetModel;
    private InternetSubResponse InternetSubModel;
    private PrintedMediaSubResponse printedMediaSubResponse;
    private MenuListResponse menuListResponse;
    private SummaryListResponse summaryListResponse;
    private NotificationsResponse notificationsResponse;

    private DataHolder() {}

    public static DataHolder getInstance() {
        return instance;
    }

    public MediaAgendaResponse getMediaAgendaModel() {
        return mediaAgendaModel;
    }

    public void setMediaAgendaModel(MediaAgendaResponse mediaAgendaModel) {
        this.mediaAgendaModel = mediaAgendaModel;
    }

    public NewspaperFirstPagesResponse getNewspaperFirstPagesModel() {
        return newspaperFirstPagesModel;
    }

    public void setNewspaperFirstPagesModel(NewspaperFirstPagesResponse newspaperFirstPagesModel) {
        this.newspaperFirstPagesModel = newspaperFirstPagesModel;
    }

    public NewsPaperFullPagesResponse getNewsPaperFullPagesModel() {
        return newsPaperFullPagesModel;
    }

    public void setNewsPaperFullPagesModel(NewsPaperFullPagesResponse newsPaperFullPagesModel) {
        this.newsPaperFullPagesModel = newsPaperFullPagesModel;
    }

    public MagazineFullPagesResponse getMagazineFullPagesModel() {
        return magazineFullPagesModel;
    }

    public void setMagazineFullPagesModel(MagazineFullPagesResponse magazineFullPagesModel) {
        this.magazineFullPagesModel = magazineFullPagesModel;
    }

    public ColumnistsResponse getColumnistsModel() {
        return columnistsModel;
    }

    public void setColumnistsModel(ColumnistsResponse columnistsModel) {
        this.columnistsModel = columnistsModel;
    }

    public VisualMediaResponse getVisualMediaModel() {
        return visualMediaModel;
    }

    public void setVisualMediaModel(VisualMediaResponse visualMediaModel) {
        this.visualMediaModel = visualMediaModel;
    }

    public SubMenuVisualMediaResponse getSubMenuVisualMediaModel() {
        return subMenuVisualMediaModel;
    }

    public void setSubMenuVisualMediaModel(SubMenuVisualMediaResponse subMenuVisualMediaModel) {
        this.subMenuVisualMediaModel = subMenuVisualMediaModel;
    }

    public InternetResponse getInternetModel() {
        return internetModel;
    }

    public void setInternetModel(InternetResponse internetModel) {
        this.internetModel = internetModel;
    }

    public InternetSubResponse getInternetSubModel() {
        return InternetSubModel;
    }

    public void setInternetSubModel(InternetSubResponse internetSubModel) {
        InternetSubModel = internetSubModel;
    }

    public MenuListResponse getMenuListResponse() {
        return menuListResponse;
    }

    public void setMenuListResponse(MenuListResponse menuListResponse) {
        this.menuListResponse = menuListResponse;
    }

    public SummaryListResponse getSummaryListResponse() {
        return summaryListResponse;
    }

    public void setSummaryListResponse(SummaryListResponse summaryListResponse) {
        this.summaryListResponse = summaryListResponse;
    }

    public PrintedMediaSubResponse getPrintedMediaSubResponse() {
        return printedMediaSubResponse;
    }

    public void setPrintedMediaSubResponse(PrintedMediaSubResponse printedMediaSubResponse) {
        this.printedMediaSubResponse = printedMediaSubResponse;
    }

    public NotificationsResponse getNotificationsResponse() {
        return notificationsResponse;
    }

    public void setNotificationsResponse(NotificationsResponse notificationsResponse) {
        this.notificationsResponse = notificationsResponse;
    }

    public void clearDataHolderClass() {
        this.mediaAgendaModel = null;
        this.newspaperFirstPagesModel = null;
        this.newsPaperFullPagesModel = null;
        this.magazineFullPagesModel = null;
        this.columnistsModel = null;
        this.visualMediaModel = null;
        this.subMenuVisualMediaModel = null;
        this.internetModel = null;
        this.InternetSubModel = null;
        this.menuListResponse = null;
        this.summaryListResponse = null;
        this.printedMediaSubResponse = null;
        this.notificationsResponse = null;
    }
}
