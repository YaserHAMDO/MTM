package com.example.mtm.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SummaryListResponse {

    @SerializedName("data")
    private Data data;

    @SerializedName("pagination")
    private Pagination pagination;

    @SerializedName("response")
    private ResponseStatus responseStatus;

    public Data getData() {
        return data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public static class Data {
        @SerializedName("docs")
        private List<Document> documents;

        @SerializedName("menu")
        private Menu menu;

        public List<Document> getDocuments() {
            return documents;
        }

        public Menu getMenu() {
            return menu;
        }
    }

    public static class Document {
        @SerializedName("cdate")
        private String createDate;

        @SerializedName("clips")
        private Clips clips;

        @SerializedName("id")
        private int id;

        // Add more fields as needed

        public String getCreateDate() {
            return createDate;
        }

        public Clips getClips() {
            return clips;
        }

        public int getId() {
            return id;
        }
    }

    public static class Clips {
        @SerializedName("pm")
        private Pm pm;

        public Pm getPm() {
            return pm;
        }
    }

    public static class Pm {
        @SerializedName("docs")
        private List<Doc> docs;

        @SerializedName("pagination")
        private Pagination pagination;

        public List<Doc> getDocs() {
            return docs;
        }

        public Pagination getPagination() {
            return pagination;
        }
    }

    public static class Doc {
        // Define fields for Doc class based on the JSON structure
        // For simplicity, I'm omitting the fields here. You can add them as needed.
    }

    public static class Pagination {
        @SerializedName("currentPage")
        private int currentPage;

        @SerializedName("perPage")
        private int perPage;

        @SerializedName("totalPage")
        private int totalPage;

        @SerializedName("totalRow")
        private int totalRow;

        @SerializedName("isLastPage")
        private boolean isLastPage;

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

    public static class ResponseStatus {
        @SerializedName("status")
        private boolean status;

        public boolean isStatus() {
            return status;
        }
    }

    public static class Menu {
        @SerializedName("menu")
        private List<MenuData> menuDataList;

        public List<MenuData> getMenuDataList() {
            return menuDataList;
        }
    }

    public static class MenuData {
        @SerializedName("id")
        private int id;

        @SerializedName("name")
        private String name;

        @SerializedName("subMenus")
        private List<SubMenu> subMenus;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public List<SubMenu> getSubMenus() {
            return subMenus;
        }
    }

    public static class SubMenu {
        @SerializedName("id")
        private int id;

        @SerializedName("name")
        private String name;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
