package com.example.mtm;

import java.util.ArrayList;
import java.util.Date;

public class SubMenuVisualMediaModel {

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

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public ArrayList<Tag> getTags() {
            return tags;
        }
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
        public String url;
        public boolean img_clip;
        public boolean img_screen;
        public String srcType;
        public String srcFirstSeen;
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

        public String getUrl() {
            return url;
        }

        public boolean isImg_clip() {
            return img_clip;
        }

        public boolean isImg_screen() {
            return img_screen;
        }

        public String getSrcType() {
            return srcType;
        }

        public String getSrcFirstSeen() {
            return srcFirstSeen;
        }

        public String getImageStoragePath() {
            return imageStoragePath;
        }
    }

    public class Media{
        public int id;
        public String name;
        public Type type;
        public Date cdatetime;
        public String priority;
        public String logo;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Type getType() {
            return type;
        }

        public Date getCdatetime() {
            return cdatetime;
        }

        public String getPriority() {
            return priority;
        }

        public String getLogo() {
            return logo;
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

    public class Type{
        public int id;
        public String name;
        public Date cdatetime;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Date getCdatetime() {
            return cdatetime;
        }
    }
}
