package Model;

public class Post {
    private int postID;
    private int accountID;
    private String title;
    private String dateCreated;
    private String postbanner;
    private String context;
    private String description;
    private int postCategoryID;
    private int views;

    // Constructor mặc định
    public Post() {
    }

    // Constructor đầy đủ tham số
    public Post(int postID, int accountID, String title, String dateCreated, 
                String postbanner, String context, String description, 
                int postCategoryID, int views) {
        this.postID = postID;
        this.accountID = accountID;
        this.title = title;
        this.dateCreated = dateCreated;
        this.postbanner = postbanner;
        this.context = context;
        this.description = description;
        this.postCategoryID = postCategoryID;
        this.views = views;
    }

    // Getters và Setters
    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getPostbanner() {
        return postbanner;
    }

    public void setPostbanner(String postbanner) {
        this.postbanner = postbanner;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPostCategoryID() {
        return postCategoryID;
    }

    public void setPostCategoryID(int postCategoryID) {
        this.postCategoryID = postCategoryID;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
