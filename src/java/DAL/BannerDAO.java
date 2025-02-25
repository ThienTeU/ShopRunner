package DAL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Model.Banner;

public class BannerDAO extends DBContext {
    public List<Banner> getAllBanners() {
        List<Banner> banners = new ArrayList<>();
        String query = "SELECT * FROM banner WHERE status = 1 ORDER BY display_order";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Banner banner = new Banner(
                        rs.getInt("banner_id"),
                        rs.getString("image_url"),
                        rs.getString("link_url"),
                        rs.getInt("display_order"),
                        rs.getBoolean("status")
                );
                banners.add(banner);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return banners;
    }
    
    
    
    public static void main(String[] args) {
        BannerDAO bannerDAO = new BannerDAO();
        List<Banner> banners = bannerDAO.getAllBanners();

        // Kiểm tra kết quả lấy được từ database
        if (banners.isEmpty()) {
            System.out.println("Không có banner nào được tìm thấy!");
        } else {
            System.out.println("Danh sách banner:");
            for (Banner b : banners) {
                System.out.println("ID: " + b.getBanner_id()+ 
                                   ", Image: " + b.getImage_url()+ 
                                   ", Link: " + b.getLink_url() + 
                                   ", Order: " + b.getDisplay_order() + 
                                   ", Status: " + b.isStatus());
            }
        }
    }
}
