package Model;

import java.util.Date;

public class Favorite {
    private int favorite_id;
    private int user_id;
    private int product_id;
    private Date created_at;

    public Favorite() {
    }

    public Favorite(int favorite_id, int user_id, int product_id, Date created_at) {
        this.favorite_id = favorite_id;
        this.user_id = user_id;
        this.product_id = product_id;
        this.created_at = created_at;
    }

    public int getFavorite_id() {
        return favorite_id;
    }

    public void setFavorite_id(int favorite_id) {
        this.favorite_id = favorite_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "favorite_id=" + favorite_id +
                ", user_id=" + user_id +
                ", product_id=" + product_id +
                ", created_at=" + created_at +
                '}';
    }
}
