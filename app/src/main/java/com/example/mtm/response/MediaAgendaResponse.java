package com.example.mtm.response;

import java.util.ArrayList;

public class MediaAgendaResponse {

    public Data data;
    public Response response;

    public Data getData() {
        return data;
    }

    public Response getResponse() {
        return response;
    }

    public class AgendaType{
        public int id;
        public String name;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public class Bc{
        public ArrayList<Doc> docs;
        public Pagination pagination;

        public ArrayList<Doc> getDocs() {
            return docs;
        }

        public Pagination getPagination() {
            return pagination;
        }
    }

    public class Clips{
        public Pm pm;
        public Bc bc;

        public Pm getPm() {
            return pm;
        }

        public Bc getBc() {
            return bc;
        }
    }

    public class Contents{
        public TrTR tr_TR;

        public TrTR getTr_TR() {
            return tr_TR;
        }
    }

    public class Data{
        public ArrayList<Doc> docs;

        public ArrayList<Doc> getDocs() {
            return docs;
        }
    }

    public class Distribution{
        public int id;
        public String name;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public class Doc{
        public String cdate;
        public Clips clips;
        public int id;
        public String date;
        public AgendaType agendaType;
        public int agendaOrder;
        public int headlineOrder;
        public boolean hasImage;
        public int cuser;
        public Contents contents;
        public String imageUrl;
        public boolean headline;
        public Object gno;
        public String gnoHash;
        public Object mtoGno;
        public String title;
        public String publishDate;
        public String ctime;
        public int newsType;
        public Media media;
        public ArrayList<Tag> tags;
        public Distribution distribution;
        public Page page;
        public double area;
        public Object pclip;
        public boolean isFullPage;
        public String imageStoragePath;
        public Program program;
        public String duration;
        public String video;
        public String publishTime;


        public String getCdate() {
            return cdate;
        }

        public Clips getClips() {
            return clips;
        }

        public int getId() {
            return id;
        }

        public String getDate() {
            return date;
        }

        public AgendaType getAgendaType() {
            return agendaType;
        }

        public int getAgendaOrder() {
            return agendaOrder;
        }

        public int getHeadlineOrder() {
            return headlineOrder;
        }

        public boolean isHasImage() {
            return hasImage;
        }

        public int getCuser() {
            return cuser;
        }

        public Contents getContents() {
            return contents;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public boolean isHeadline() {
            return headline;
        }

        public Object getGno() {
            return gno;
        }

        public String getGnoHash() {
            return gnoHash;
        }

        public Object getMtoGno() {
            return mtoGno;
        }

        public String getTitle() {
            return title;
        }

        public String getPublishDate() {
            return publishDate;
        }

        public String getCtime() {
            return ctime;
        }

        public int getNewsType() {
            return newsType;
        }

        public Media getMedia() {
            return media;
        }

        public ArrayList<Tag> getTags() {
            return tags;
        }

        public Distribution getDistribution() {
            return distribution;
        }

        public Page getPage() {
            return page;
        }

        public double getArea() {
            return area;
        }

        public Object getPclip() {
            return pclip;
        }

        public boolean isFullPage() {
            return isFullPage;
        }

        public String getImageStoragePath() {
            return imageStoragePath;
        }

        public Program getProgram() {
            return program;
        }

        public String getDuration() {
            return duration;
        }

        public String getVideo() {
            return video;
        }

        public String getPublishTime() {
            return publishTime;
        }
    }

    public class Media{
        public int id;
        public String name;
        public int pageCount;
        public String cdate;
        public double circulation;
        public Object gno;
        public String infoclip;
        public String icHash;
        public String imageFirstPage;
        public String logo;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getPageCount() {
            return pageCount;
        }

        public String getCdate() {
            return cdate;
        }

        public double getCirculation() {
            return circulation;
        }

        public Object getGno() {
            return gno;
        }

        public String getInfoclip() {
            return infoclip;
        }

        public String getIcHash() {
            return icHash;
        }

        public String getImageFirstPage() {
            return imageFirstPage;
        }

        public String getLogo() {
            return logo;
        }
    }

    public class Page{
        public int no;
        public String thumbnailImagePath;
        public String fullImagePath;

        public int getNo() {
            return no;
        }

        public String getThumbnailImagePath() {
            return thumbnailImagePath;
        }

        public String getFullImagePath() {
            return fullImagePath;
        }
    }

    public class Pagination{
        public int currentPage;
        public int perPage;
        public int totalPage;
        public int totalRow;
        public boolean isLastPage;

        public int getCurrentPage() {
            return currentPage;
        }

        public int getPerPage() {
            return perPage;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public int getTotalRow() {
            return totalRow;
        }

        public boolean isLastPage() {
            return isLastPage;
        }
    }

    public class Pm{
        public ArrayList<Doc> docs;
        public Pagination pagination;

        public ArrayList<Doc> getDocs() {
            return docs;
        }

        public Pagination getPagination() {
            return pagination;
        }
    }

    public class Program{
        public int id;

        public int getId() {
            return id;
        }
    }

    public class Response{
        public boolean status;

        public boolean isStatus() {
            return status;
        }
    }

    public class Tag{
        public int id;

        public int getId() {
            return id;
        }
    }

    public class TrTR{
        public String language;
        public String title;
        public String text;

        public String getLanguage() {
            return language;
        }

        public String getTitle() {
            return title;
        }

        public String getText() {
            return text;
        }
    }

}
