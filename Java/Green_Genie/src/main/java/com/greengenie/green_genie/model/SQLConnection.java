package com.greengenie.green_genie.model;

import org.json.simple.JSONObject;

public class SQLConnection {
    /*
     * ds.setUrl("jdbc:mysql://localhost:3306/groepc?useSSL=false&serverTimezone=UTC");
     * ds.setUsername("JFXAPP");
     * ds.setPassword("35if1euOezkHGQAPtAxy");
     */
    String PREFIX = "jdbc:mysql:";
    String URL = "";
    String PORT = "3306";
    String DATABASE = "";
    Boolean USERSSL = false;
    String SERVERTIMEZONE = "UTC";
    String USERNAME = "";
    String PASSWORD = "";

    public SQLConnection() {
    }

    public SQLConnection(String PREFIX, String URL, String PORT, String DATABASE, Boolean USERSSL, String SERVERTIMEZONE, String USERNAME, String PASSWORD) {
        this.PREFIX = PREFIX;
        this.URL = URL;
        this.PORT = PORT;
        this.DATABASE = DATABASE;
        this.USERSSL = USERSSL;
        this.SERVERTIMEZONE = SERVERTIMEZONE;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
    }

    public SQLConnection(JSONObject jsonObject) {
        fromnJSONObject(jsonObject);
    }

    public String getPREFIX() {
        return PREFIX;
    }

    public void setPREFIX(String PREFIX) {
        this.PREFIX = PREFIX;
    }

    public String getURL() {
        return URL;
    }

    public String getFullURL() {
        //     jdbc:mysql://localhost:3306    /groepc      ?useSSL=false      &serverTimezone=UTC
        return PREFIX   +"//"+URL  +":"+PORT+"/"+DATABASE+"?useSSL="+USERSSL+"&serverTimezone="+SERVERTIMEZONE;
    }


    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getPORT() {
        return PORT;
    }

    public void setPORT(String PORT) {
        this.PORT = PORT;
    }

    public String getDATABASE() {
        return DATABASE;
    }

    public void setDATABASE(String DATABASE) {
        this.DATABASE = DATABASE;
    }

    public Boolean getUSERSSL() {
        return USERSSL;
    }

    public void setUSERSSL(Boolean USERSSL) {
        this.USERSSL = USERSSL;
    }

    public String getSERVERTIMEZONE() {
        return SERVERTIMEZONE;
    }

    public void setSERVERTIMEZONE(String SERVERTIMEZONE) {
        this.SERVERTIMEZONE = SERVERTIMEZONE;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }




    /**
     * Creates a string to make an JSONObject from. To be an actual JSONOObject it needs '{' at the start and '}'behind it.
     * @return a string as an incomplete JSONObject.
     */
    public JSONObject toJSONObject(){
        JSONObject jObjct = new JSONObject();
        jObjct.put("PREFIX", PREFIX);
        jObjct.put("URL", URL);
        jObjct.put("PORT", PORT);
        jObjct.put("DATABASE", DATABASE);
        jObjct.put("USERSSL", USERSSL);
        jObjct.put("SERVERTIMEZONE", SERVERTIMEZONE);
        jObjct.put("USERNAME", USERNAME);
        jObjct.put("PASSWORD", PASSWORD);
        return jObjct;
    }

    public void fromnJSONObject(JSONObject jsonObject) {
        this.PREFIX = (String) jsonObject.get("PREFIX");
        this.URL = (String) jsonObject.get("URL");
        this.PORT = (String) jsonObject.get("PORT");
        this.DATABASE = (String) jsonObject.get("DATABASE");
        this.USERSSL = (Boolean) jsonObject.get("USERSSL");
        this.SERVERTIMEZONE = (String) jsonObject.get("SERVERTIMEZONE");
        this.USERNAME = (String) jsonObject.get("USERNAME");
        this.PASSWORD = (String) jsonObject.get("PASSWORD");
    }


    public Boolean filled(){
        return (this.PREFIX != null && this.URL != null && this.PORT != null && this.DATABASE != null && this.SERVERTIMEZONE != null && this.USERNAME != null && this.PASSWORD != null &&
                !this.PREFIX.isEmpty() && !this.URL.isEmpty() && !this.PORT.isEmpty() && !this.DATABASE.isEmpty() && !this.SERVERTIMEZONE.isEmpty() && !this.USERNAME.isEmpty() && !this.PASSWORD.isEmpty());
    }

}
