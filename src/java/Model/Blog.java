/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author admin
 */
public class Blog {
    private int blog_id;
    private String title;
    private String content;
    private String author;
    private String create_at;
    private boolean status;
    private String thumbnail;

    public Blog() {
    }

    public Blog(int blog_id, String title, String content, String author, String create_at, boolean status, String thumbnail) {
        this.blog_id = blog_id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.create_at = create_at;
        this.status = status;
        this.thumbnail = thumbnail;
    }

    public int getBlog_id() {
        return blog_id;
    }

    public void setBlog_id(int blog_id) {
        this.blog_id = blog_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Blog{");
        sb.append("blog_id=").append(blog_id);
        sb.append(", title=").append(title);
        sb.append(", content=").append(content);
        sb.append(", author=").append(author);
        sb.append(", create_at=").append(create_at);
        sb.append(", status=").append(status);
        sb.append(", thumbnail=").append(thumbnail);
        sb.append('}');
        return sb.toString();
    }
    
}
