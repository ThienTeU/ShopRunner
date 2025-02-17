package Model;

public class ProductView {

    private int view_id;
    private int product_id;
    private int view;
    private String viewed_at;

    public ProductView(int product_id) {
        this.product_id = product_id;
    }

    public ProductView(int view_id, int product_id, int view, String viewed_at) {
        this.view_id = view_id;
        this.product_id = product_id;
        this.view = view;
        this.viewed_at = viewed_at;
    }
    

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }   
    public int getView_id() {
        return view_id;
    }

    public void setView_id(int view_id) {
        this.view_id = view_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }


    public String getViewed_at() {
        return viewed_at;
    }

    public void setViewed_at(String viewed_at) {
        this.viewed_at = viewed_at;
    }
}
