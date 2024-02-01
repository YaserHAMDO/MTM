package com.example.mtm;

import java.util.ArrayList;

public class NewspaperFirstPagesModel {

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
        public Object gno;
        public int id;
        public String date;
        public String name;
        public String logo;
        public String imageFirstPage;
        public ImageInfo imageInfo;

        public Object getGno() {
            return gno;
        }

        public int getId() {
            return id;
        }

        public String getDate() {
            return date;
        }

        public String getName() {
            return name;
        }

        public String getLogo() {
            return logo;
        }

        public String getImageFirstPage() {
            return imageFirstPage;
        }

        public ImageInfo getImageInfo() {
            return imageInfo;
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
