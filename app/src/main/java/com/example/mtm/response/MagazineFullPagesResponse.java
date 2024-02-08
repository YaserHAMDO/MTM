package com.example.mtm.response;

import java.util.ArrayList;

public class MagazineFullPagesResponse {

    public ArrayList<Datum> data;
    public Pagination pagination;
    public Response response;

    public ArrayList<Datum> getData() {
        return data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public Response getResponse() {
        return response;
    }

    public class Datum{
        public int id;
        public String name;
        public String cdate;
        public double circulation;
        public String date;
        public Object gno;
        public ArrayList<Page> pages;
        public ImageInfo imageInfo;
        public String imageFirstPage;
        public String logo;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getCdate() {
            return cdate;
        }

        public double getCirculation() {
            return circulation;
        }

        public String getDate() {
            return date;
        }

        public Object getGno() {
            return gno;
        }

        public ArrayList<Page> getPages() {
            return pages;
        }

        public ImageInfo getImageInfo() {
            return imageInfo;
        }

        public String getImageFirstPage() {
            return imageFirstPage;
        }

        public String getLogo() {
            return logo;
        }
    }

    public class ImageInfo{
        public String mediaPath;
        public String pageFile;

        public String getMediaPath() {
            return mediaPath;
        }

        public String getPageFile() {
            return pageFile;
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
    }

    public class Response{
        public boolean status;

        public boolean isStatus() {
            return status;
        }
    }

}
