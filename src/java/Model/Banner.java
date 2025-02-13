/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author admin
 */
public class Banner {
    private int banner_id;
    private String image_url;
    private String link_url;
    private int display_order;
    private boolean status;

    public Banner() {
    }

    public Banner(int banner_id, String image_url, String link_url, int display_order, boolean status) {
        this.banner_id = banner_id;
        this.image_url = image_url;
        this.link_url = link_url;
        this.display_order = display_order;
        this.status = status;
    }

    public int getBanner_id() {
        return banner_id;
    }

    public void setBanner_id(int banner_id) {
        this.banner_id = banner_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

    public int getDisplay_order() {
        return display_order;
    }

    public void setDisplay_order(int display_order) {
        this.display_order = display_order;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Banner{");
        sb.append("banner_id=").append(banner_id);
        sb.append(", image_url=").append(image_url);
        sb.append(", link_url=").append(link_url);
        sb.append(", display_order=").append(display_order);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
    
}
