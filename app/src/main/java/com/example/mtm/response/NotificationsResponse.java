package com.example.mtm.response;

import java.util.ArrayList;
import java.util.Date;

public class NotificationsResponse {

    public ArrayList<Data> data;
    public Pagination pagination;
    public Response response;


    public ArrayList<Data> getData() {
        return data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public Response getResponse() {
        return response;
    }

    public class Data{
        public News news;
        public String url;
        public int id;
        public String title;
        public String body;
        public int account;
        public Data data;
        public String cdatetime;
        public String rtime;

        public News getNews() {
            return news;
        }

        public String getUrl() {
            return url;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getBody() {
            return body;
        }

        public int getAccount() {
            return account;
        }

        public Data getData() {
            return data;
        }

        public String getCdatetime() {
            return cdatetime;
        }

        public String getRtime() {
            return rtime;
        }
    }

    public class News{
        public ArrayList<Long> printMedia;

        public ArrayList<Long> getPrintMedia() {
            return printMedia;
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

    }




}
