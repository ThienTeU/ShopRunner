package Model;

public class PostCategory {
    private int postCategoryID;
    private String name;

    public PostCategory() {
    }

    public PostCategory(int postCategoryID, String name) {
        this.postCategoryID = postCategoryID;
        this.name = name;
    }

    public int getPostCategoryID() {
        return postCategoryID;
    }

    public void setPostCategoryID(int postCategoryID) {
        this.postCategoryID = postCategoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
