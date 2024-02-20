package com.example.mtm.response;

import java.util.ArrayList;

public class PrintedMediaSubResponse {
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

    public class Tag{
        public int id;

        public int getId() {
            return id;
        }
    }

    public class TagMap{
        public int id;
        public String name;
        public ArrayList<DataSplitter> dataSplitters;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public ArrayList<DataSplitter> getDataSplitters() {
            return dataSplitters;
        }
    }



    public class City{
        public int id;
        public String name;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public class ContinuesClip{
        public int pn;
        public String fl;
        public Object gno;
        public boolean fp;
        public double area;
        public boolean tm;

        public int getPn() {
            return pn;
        }

        public String getFl() {
            return fl;
        }

        public Object getGno() {
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

    public class DataSplitter{
        public int id;
        public String name;
        public ArrayList<Tag> tags;
    }

    public class Doc{
        public Object gno;
        public String gnoHash;
        public Object mtoGno;
        public String title;
        public String publishDate;
        public String cdate;
        public String ctime;
        public int newsType;
        public Media media;
        public ArrayList<TagMap> tagMap;
        public Page page;
        public double area;
        public Object pclip;
        public boolean isFullPage;
        public ImageInfo imageInfo;
        public ArrayList<ContinuesClip> continuesClip;
        public String imageStoragePath;

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

        public ArrayList<TagMap> getTagMap() {
            return tagMap;
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

        public ImageInfo getImageInfo() {
            return imageInfo;
        }

        public ArrayList<ContinuesClip> getContinuesClip() {
            return continuesClip;
        }

        public String getImageStoragePath() {
            return imageStoragePath;
        }
    }

    public class ImageInfo{
        public String clipPg;
        public String mediaPath;
        public String clipFile;
        public String pageFile;
        public double pageWith;
        public double pageHeigth;

        public String getClipPg() {
            return clipPg;
        }

        public String getMediaPath() {
            return mediaPath;
        }

        public String getClipFile() {
            return clipFile;
        }

        public String getPageFile() {
            return pageFile;
        }

        public double getPageWith() {
            return pageWith;
        }

        public double getPageHeigth() {
            return pageHeigth;
        }
    }

    public class Media{
        public int id;
        public String name;
        public int pageCount;
        public String cdate;
        public double circulation;
        public City city;
        public MediaType mediaType;
        public Object gno;
        public String infoclip;
        public String icHash;
        public double accessInformation;
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

        public City getCity() {
            return city;
        }

        public MediaType getMediaType() {
            return mediaType;
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

        public double getAccessInformation() {
            return accessInformation;
        }

        public String getImageFirstPage() {
            return imageFirstPage;
        }

        public String getLogo() {
            return logo;
        }
    }

    public class MediaType{
        public int id;
        public String name;
        public double accessfactor;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public double getAccessfactor() {
            return accessfactor;
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

    public class Response{
        public boolean status;

        public boolean isStatus() {
            return status;
        }
    }


}
