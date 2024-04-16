package com.medyatakip.app.response;

import java.util.ArrayList;

public class CurrentUserResponse {
    public Data data;
    public Response response;

    public Data getData() {
        return data;
    }

    public Response getResponse() {
        return response;
    }

    public class User{
        public String uuid;
        public String name;
        public Object lastname;
        public boolean active;
        public int lastaccount;
        public String email;

        public String getUuid() {
            return uuid;
        }

        public String getName() {
            return name;
        }

        public Object getLastname() {
            return lastname;
        }

        public boolean isActive() {
            return active;
        }

        public int getLastaccount() {
            return lastaccount;
        }

        public String getEmail() {
            return email;
        }
    }

    public class BC{
        public String start;
        public String end;

        public String getStart() {
            return start;
        }

        public String getEnd() {
            return end;
        }
    }

    public class Data{
        public Daterange daterange;
        public ArrayList<String> permissions;
        public User user;
        public DataSource dataSource;

        public Daterange getDaterange() {
            return daterange;
        }

        public ArrayList<String> getPermissions() {
            return permissions;
        }

        public User getUser() {
            return user;
        }

        public DataSource getDataSource() {
            return dataSource;
        }
    }

    public class DataSource{
        public int PM;
        public int DM;
        public int BC;
        public int NA;


        public int getPM() {
            return PM;
        }

        public int getDM() {
            return DM;
        }

        public int getBC() {
            return BC;
        }

        public int getNA() {
            return NA;
        }
    }

    public class Daterange{
        public BC BC;
        public NA NA;
        public DM DM;
        public PM PM;

        public CurrentUserResponse.BC getBC() {
            return BC;
        }

        public CurrentUserResponse.NA getNA() {
            return NA;
        }

        public CurrentUserResponse.DM getDM() {
            return DM;
        }

        public CurrentUserResponse.PM getPM() {
            return PM;
        }
    }

    public class DM{
        public String start;
        public String end;

        public String getStart() {
            return start;
        }

        public String getEnd() {
            return end;
        }
    }

    public class NA{
        public String start;
        public String end;

        public String getStart() {
            return start;
        }

        public String getEnd() {
            return end;
        }
    }

    public class PM{
        public String start;
        public String end;

        public String getStart() {
            return start;
        }

        public String getEnd() {
            return end;
        }
    }

    public class Response{
        public boolean status;

        public boolean isStatus() {
            return status;
        }
    }

}
