/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO.DucAnh;

/**
 *
 * @author 1234
 */
public class PostDTO {

    private int postID;
    private String title;
    private String dateCreated;
    private String postImg;
    private String description;
    private String context;
    private String category;
    private int views;


    public PostDTO(int postID, String title, String dateCreated, String postImg, String description, String context, String category) {
        this.postID = postID;
        this.title = title;
        this.dateCreated = dateCreated;
        this.postImg = postImg;
        this.description = description;
        this.context = context;
        this.category = category;
        this.views = views;
    }
    
     public PostDTO(int postID, String title, String dateCreated, String postImg, String description, String context, String category, int views) {
        this.postID = postID;
        this.title = title;
        this.dateCreated = dateCreated;
        this.postImg = postImg;
        this.description = description;
        this.context = context;
        this.category = category;
        this.views = views;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getViews() {
        return views;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getPostImg() {
        return postImg;
    }

    public void setPostImg(String postImg) {
        this.postImg = postImg;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PostDTO() {
    }

}
