package spencerstudios.com.codespace;


class Data {

    private String title;
    private String description;
    private String contributor;
    private String link;
    private String key;
    private long timeStamp;

    public Data(String description, String contributor, String link, long timeStamp, String title,String key) {
        this.title = title;
        this.description = description;
        this.contributor = contributor;
        this.link = link;
        this.timeStamp = timeStamp;
        this.key = key;
    }

    public Data(String description, String contributor, String link, long timeStamp, String title) {
        this.title = title;
        this.description = description;
        this.contributor = contributor;
        this.link = link;
        this.timeStamp = timeStamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public String getContributor() {
        return contributor;
    }

    public String getLink() {
        return link;
    }


    public long getTimeStamp() {
        return timeStamp;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
