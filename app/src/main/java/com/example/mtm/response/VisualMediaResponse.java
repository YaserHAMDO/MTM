package com.example.mtm.response;

import java.util.ArrayList;

public class VisualMediaResponse {

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

    public class SubMenu{
        public int id;
        public String name;
        public int point;
        public int docCount;
        public int newsCount;
        public int adsCount;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getPoint() {
            return point;
        }

        public int getDocCount() {
            return docCount;
        }

        public int getNewsCount() {
            return newsCount;
        }

        public int getAdsCount() {
            return adsCount;
        }
    }

    public class Data{
        public Menu menu;

        public Menu getMenu() {
            return menu;
        }
    }

    public class Menu{
        public int docCount;
        public ArrayList<Menu> menu;
        public int id;
        public String name;
        public int point;
        public ArrayList<SubMenu> subMenus;
        public int newsCount;
        public int adsCount;

        public int getDocCount() {
            return docCount;
        }

        public ArrayList<Menu> getMenu() {
            return menu;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getPoint() {
            return point;
        }

        public ArrayList<SubMenu> getSubMenus() {
            return subMenus;
        }

        public int getNewsCount() {
            return newsCount;
        }

        public int getAdsCount() {
            return adsCount;
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
