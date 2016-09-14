package vladimiroff.csu.foodrss;

public class SingleItem {
    private String pubDate;
    private String title;
    private String description;
    private String link;

    public String getPubDate() { return pubDate; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getLink() { return link; }

    public SingleItem(String pubDate, String title, String description, String link) {
        this.pubDate = pubDate;
        this.title = title;
        this.description = description;
        this.link = link;
    }

    @Override
    public String toString() {
        return title;
    }
}
