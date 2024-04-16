package com.medyatakip.app.response;

import java.util.ArrayList;

public class SubMenuVisualMediaResponse {

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

    public class Data {
        public ArrayList<Doc> docs;

        public ArrayList<Doc> getDocs() {
            return docs;
        }
    }

    public class DataSplitter {
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

    public class Doc {
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
        public Program program;
        public AdsVersion adsVersion;
        public String duration;
        public String video;
        public String publishTime;
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

        public Program getProgram() {
            return program;
        }

        public String getDuration() {
            return duration;
        }

        public String getVideo() {
            return video;
        }

        public AdsVersion getAdsVersion() {
            return adsVersion;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public String getImageStoragePath() {
            return imageStoragePath;
        }
    }

    public class Media {
        public int id;
        public String name;
        public String type;
        public String logo;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public String getLogo() {
            return logo;
        }
    }

    public class Pagination {
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

    public class Program {
        public int id;
        public String name;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public class AdsType{
        public int id;
        public String name;
    }

    public class AdsVersion{
        public int id;
        public String name;
        public String cdate;
        public String filename;
        public AdsType adsType;
        public String media;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getCdate() {
            return cdate;
        }

        public String getFilename() {
            return filename;
        }

        public AdsType getAdsType() {
            return adsType;
        }

        public String getMedia() {
            return media;
        }
    }

    public class Response {
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
}