package DAL;

import Model.Banner;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BannerDAO extends DBContext {
    private static final Logger LOGGER = Logger.getLogger(BannerDAO.class.getName());

        public List<Banner> getAllBanners() {
            List<Banner> banners = new ArrayList<>();
            String query = "SELECT * FROM banner ORDER BY display_order";

            try (PreparedStatement ps = connection.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {

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
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error getting all banners", e);
            }
            return banners;
        }

    public Banner getBannerById(int id) {
        String query = "SELECT * FROM banner WHERE banner_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Banner(
                        rs.getInt("banner_id"),
                        rs.getString("image_url"),
                        rs.getString("link_url"),
                        rs.getInt("display_order"),
                        rs.getBoolean("status")
                    );
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting banner by ID: " + id, e);
        }
        return null;
    }

    public boolean addBanner(Banner banner) {
        String query = "INSERT INTO banner (image_url, link_url, display_order, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, banner.getImage_url());
            ps.setString(2, banner.getLink_url());
            ps.setInt(3, banner.getDisplay_order());
            ps.setBoolean(4, banner.isStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error adding banner", e);
            return false;
        }
    }

public boolean updateBanner(Banner banner) {
    String query = "UPDATE banner SET image_url=?, link_url=?, display_order=?, status=? WHERE banner_id=?";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
        ps.setString(1, banner.getImage_url());
        ps.setString(2, banner.getLink_url());
        ps.setInt(3, banner.getDisplay_order());
        ps.setBoolean(4, banner.isStatus());
        ps.setInt(5, banner.getBanner_id());

        int rowsAffected = ps.executeUpdate();
        LOGGER.log(Level.INFO, "Updated {0} rows with banner_id {1}", new Object[]{rowsAffected, banner.getBanner_id()});
        return rowsAffected > 0;
    } catch (SQLException e) {
        LOGGER.log(Level.SEVERE, "Error updating banner", e);
        return false;
    }
}


    public boolean deleteBanner(int id) {
        String query = "DELETE FROM banner WHERE banner_id=?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting banner", e);
            return false;
        }
    }

    public int getMaxDisplayOrder() {
        String query = "SELECT MAX(display_order) FROM banner";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting max display order", e);
        }
        return 0;
    }

    public boolean moveBannerUp(int bannerId) {
        try {
            connection.setAutoCommit(false);
            Banner currentBanner = getBannerById(bannerId);
            if (currentBanner == null) return false;

            String query = "SELECT * FROM banner WHERE display_order < ? ORDER BY display_order DESC LIMIT 1";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, currentBanner.getDisplay_order());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int otherBannerId = rs.getInt("banner_id");
                        int otherOrder = rs.getInt("display_order");
                        
                        // Swap orders
                        swapDisplayOrders(bannerId, currentBanner.getDisplay_order(), 
                                       otherBannerId, otherOrder);
                        
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error moving banner up", e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, "Error rolling back transaction", ex);
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error resetting auto commit", e);
            }
        }
    }

    private void swapDisplayOrders(int banner1Id, int order1, int banner2Id, int order2) throws SQLException {
        String updateQuery = "UPDATE banner SET display_order = ? WHERE banner_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(updateQuery)) {
            // Update first banner
            ps.setInt(1, order2);
            ps.setInt(2, banner1Id);
            ps.executeUpdate();

            // Update second banner
            ps.setInt(1, order1);
            ps.setInt(2, banner2Id);
            ps.executeUpdate();
        }
    }
    
    public boolean isDisplayOrderExists(int displayOrder, int excludeBannerId) {
        String query = "SELECT COUNT(*) FROM banner WHERE display_order = ? AND banner_id != ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, displayOrder);
            ps.setInt(2, excludeBannerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error checking display order", e);
        }
        return false;
    }

    public boolean isDisplayOrderExists(int displayOrder) {
        return isDisplayOrderExists(displayOrder, -1);
    }
    
    
        public List<Banner> getAllBannersOn() {
        List<Banner> banners = new ArrayList<>();
        String query = "SELECT * FROM banner WHERE status = 1 ORDER BY display_order ASC";

        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

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
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting all banners", e);
        }
        return banners;
    }

    public static void main(String[] args) {
        BannerDAO bannerDAO = new BannerDAO();
Banner banner = new Banner(1, 
    "https://lambanner.com/wp-content/uploads/2022/10/MNT-DESIGN-BANNER-DEP-DOWNLOAD-MIEN-PHI-update-1130x570.jpg",
    "https://example.com",
    1, true);

boolean success = bannerDAO.updateBanner(banner);
System.out.println(success ? "Cập nhật thành công!" : "Cập nhật thất bại!");

    }
}