package spencerstudios.com.codespace;


class Data {

    private String title, description, contributor, link;
    private long timeStamp;

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
}
