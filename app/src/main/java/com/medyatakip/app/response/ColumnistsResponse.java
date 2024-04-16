package com.medyatakip.app.response;

import java.util.ArrayList;

public class ColumnistsResponse {

    public Data data;
    public Pagination pagination;
    public Response response;

    public Data getData() {
        return data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public Response getResponse() {
        return response;
    }

    public class ContinuesClip{
        public int pn;
        public String fl;
        public long gno;
        public boolean fp;
        public double area;
        public boolean tm;

        public int getPn() {
            return pn;
        }

        public String getFl() {
            return fl;
        }

        public long getGno() {
            return gno;
        }

        public boolean isFp() {
            return fp;
        }

        public double getArea() {
            return area;
        }

        public boolean isTm() {
            return tm;
        }
    }

    public class Data{
        public ArrayList<Doc> docs;

        public ArrayList<Doc> getDocs() {
            return docs;
        }
    }

    public class Doc{
        public long gno;
        public String gnoHash;
        public Object mtoGno;
        public String title;
        public String publishDate;
        public String cdate;
        public String ctime;
        public int newsType;
        public Media media;
        public Page page;
        public double area;
        public Object pclip;
        public Journalist journalist;
        public boolean isFullPage;
        public ArrayList<ContinuesClip> continuesClip;
        public String imageStoragePath;

        public long getGno() {
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

        public String getCdate() {
            return cdate;
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

        public Page getPage() {
            return page;
        }

        public double getArea() {
            return area;
        }

        public Object getPclip() {
            return pclip;
        }

        public Journalist getJournalist() {
            return journalist;
        }

        public boolean isFullPage() {
            return isFullPage;
        }

        public ArrayList<ContinuesClip> getContinuesClip() {
            return continuesClip;
        }
        public String getImageStoragePath() {
            return imageStoragePath;
        }
    }

    public class Journalist{
        public int id;
        public String name;
        public String imagePath;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getImagePath() {
            return imagePath;
        }
    }

    public class Media{
        public int id;
        public String name;
        public int pageCount;
        public String cdate;
        public double circulation;
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

        public int getNo() {
            return no;
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

    public class Response{
        public boolean status;

        public boolean isStatus() {
            return status;
        }
    }

}
