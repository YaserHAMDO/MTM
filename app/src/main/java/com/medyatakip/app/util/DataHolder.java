package com.medyatakip.app.util;

import com.medyatakip.app.model.Model1;
import com.medyatakip.app.model.VerticalModel;
import com.medyatakip.app.response.ColumnistsResponse;
import com.medyatakip.app.response.InternetResponse;
import com.medyatakip.app.response.InternetSubResponse;
import com.medyatakip.app.response.MagazineFullPagesResponse;
import com.medyatakip.app.response.MediaAgendaResponse;
import com.medyatakip.app.response.NewsPaperFullPagesResponse;
import com.medyatakip.app.response.NewspaperFirstPagesResponse;
import com.medyatakip.app.response.NotificationsResponse;
import com.medyatakip.app.response.PrintedMediaSubResponse;
import com.medyatakip.app.response.SubMenuVisualMediaResponse;
import com.medyatakip.app.response.SummaryListResponse;

import java.util.ArrayList;

public class DataHolder {
    private static final DataHolder instance = new DataHolder();

    private MediaAgendaResponse mediaAgendaModel;
    private NewspaperFirstPagesResponse newspaperFirstPagesModel;
    private NewsPaperFullPagesResponse newsPaperFullPagesModel;
    private MagazineFullPagesResponse magazineFullPagesModel;
    private ColumnistsResponse columnistsModel;
    private InternetResponse visualMediaModel;
    private SubMenuVisualMediaResponse subMenuVisualMediaModel;
    private InternetResponse internetModel;
    private InternetSubResponse InternetSubModel;
    private PrintedMediaSubResponse printedMediaSubResponse;
    private InternetResponse menuListResponse;
    private SummaryListResponse summaryListResponse;
    private NotificationsResponse notificationsResponse;

    private ArrayList<String> columnistsShowArray;
    private ArrayList<String> printedMediaFullPageShowArray;
    private ArrayList<VerticalModel> verticalModels;
    private ArrayList<String> printedMediaShareLinkArray;
    private ArrayList<String> printedMediaSubPageShowArray;
    private ArrayList<String> printedMediaDateShowArray;
    private ArrayList<String> printedMediaNamesShowArray;

    private ArrayList<String> CaUrlArray;
    private ArrayList<String> CaShareUrlArray;
    private ArrayList<String> CaDatesArray;
    private ArrayList<String> CaNamesArray;

    private ArrayList<Model1> model1s;

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

    public InternetResponse getVisualMediaModel() {
        return visualMediaModel;
    }

    public void setVisualMediaModel(InternetResponse visualMediaModel) {
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

    public InternetResponse getMenuListResponse() {
        return menuListResponse;
    }

    public void setMenuListResponse(InternetResponse menuListResponse) {
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

    public ArrayList<String> getColumnistsShowArray() {
        return columnistsShowArray;
    }

    public void setColumnistsShowArray(ArrayList<String> columnistsShowArray) {
        this.columnistsShowArray = columnistsShowArray;
    }

    public ArrayList<String> getPrintedMediaFullPageShowArray() {
        return printedMediaFullPageShowArray;
    }

    public void setPrintedMediaFullPageShowArray(ArrayList<String> printedMediaFullPageShowArray) {
        this.printedMediaFullPageShowArray = printedMediaFullPageShowArray;
    }

    public ArrayList<String> getPrintedMediaSubPageShowArray() {
        return printedMediaSubPageShowArray;
    }

    public void setPrintedMediaSubPageShowArray(ArrayList<String> printedMediaSubPageShowArray) {
        this.printedMediaSubPageShowArray = printedMediaSubPageShowArray;
    }

    public ArrayList<String> getPrintedMediaDateShowArray() {
        return printedMediaDateShowArray;
    }

    public void setPrintedMediaDateShowArray(ArrayList<String> printedMediaDateShowArray) {
        this.printedMediaDateShowArray = printedMediaDateShowArray;
    }

    public ArrayList<String> getPrintedMediaNamesShowArray() {
        return printedMediaNamesShowArray;
    }

    public void setPrintedMediaNamesShowArray(ArrayList<String> printedMediaNamesShowArray) {
        this.printedMediaNamesShowArray = printedMediaNamesShowArray;
    }


    public ArrayList<String> getCaUrlArray() {
        return CaUrlArray;
    }

    public void setCaUrlArray(ArrayList<String> caUrlArray) {
        CaUrlArray = caUrlArray;
    }


    public ArrayList<String> getCaDatesArray() {
        return CaDatesArray;
    }

    public void setCaDatesArray(ArrayList<String> caDatesArray) {
        CaDatesArray = caDatesArray;
    }

    public ArrayList<String> getCaNamesArray() {
        return CaNamesArray;
    }

    public void setCaNamesArray(ArrayList<String> caNamesArray) {
        CaNamesArray = caNamesArray;
    }

    public ArrayList<String> getCaShareUrlArray() {
        return CaShareUrlArray;
    }

    public void setCaShareUrlArray(ArrayList<String> caShareUrlArray) {
        CaShareUrlArray = caShareUrlArray;
    }

    public ArrayList<String> getPrintedMediaShareLinkArray() {
        return printedMediaShareLinkArray;
    }

    public void setPrintedMediaShareLinkArray(ArrayList<String> printedMediaShareLinkArray) {
        this.printedMediaShareLinkArray = printedMediaShareLinkArray;
    }

    public ArrayList<VerticalModel> getVerticalModels() {
        return verticalModels;
    }

    public void setVerticalModels(ArrayList<VerticalModel> verticalModels) {
        this.verticalModels = verticalModels;
    }

    public ArrayList<Model1> getModels1() {
        return model1s;
    }

    public void setModels1(ArrayList<Model1> model1s) {
        this.model1s = model1s;
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
        this.columnistsShowArray = null;
        this.printedMediaFullPageShowArray = null;
        this.verticalModels = null;
        this.printedMediaSubPageShowArray = null;
        this.printedMediaDateShowArray = null;
        this.printedMediaNamesShowArray = null;
        this.CaUrlArray = null;
        this.CaDatesArray = null;
        this.CaNamesArray = null;
        this.CaShareUrlArray = null;
        this.printedMediaShareLinkArray = null;
        this.model1s = null;
    }
}
