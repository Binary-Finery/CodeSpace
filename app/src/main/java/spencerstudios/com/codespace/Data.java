package spencerstudios.com.codespace;


class Data {

    private String title, description, contributor, link;
    private long timeStamp;


    Data(String title, String description, String contributor, String link, long timeStamp) {
        this.title = title;
        this.description = description;
        this.contributor = contributor;
        this.link = link;
        this.timeStamp = timeStamp;
    }

    String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContributor() {
        return contributor;
    }

    public void setContributor(String contributor) {
        this.contributor = contributor;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
