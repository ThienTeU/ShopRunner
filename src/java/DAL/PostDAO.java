package DAL;

import Model.Post;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostDAO extends DBContext {
    
    // Lấy tổng số bài viết theo từ khóa tìm kiếm
    public int getTotalSearchResults(String query) {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM Post WHERE (Title LIKE ? OR Description LIKE ?)";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            String searchPattern = "%" + query + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    // Tìm kiếm bài viết với phân trang
    public List<Post> searchPosts(String query, int offset, int pageSize) {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT * FROM Post " +
                    "WHERE (Title LIKE ? OR Description LIKE ?) " +
                    "ORDER BY DateCreated DESC " +
                    "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            String searchPattern = "%" + query + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setInt(3, offset);
            ps.setInt(4, pageSize);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                posts.add(mapPost(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return posts;
    }

    // Lấy bài viết theo ID
    public Post getPostById(int postId) {
        String sql = "SELECT * FROM Post WHERE PostID = ?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, postId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapPost(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // Lấy bài viết mới nhất
    public List<Post> getLatestPosts(int limit) {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT TOP (?) * FROM Post ORDER BY DateCreated DESC";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                posts.add(mapPost(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return posts;
    }

    // Lấy bài viết phổ biến nhất
    public List<Post> getPopularPosts(int limit) {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT TOP (?) * FROM Post ORDER BY Views DESC";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                posts.add(mapPost(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return posts;
    }

    // Tăng lượt xem bài viết
    public void incrementViews(int postId) {
        String sql = "UPDATE Post SET Views = Views + 1 WHERE PostID = ?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, postId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PostDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Helper method để map ResultSet thành đối tượng Post
    private Post mapPost(ResultSet rs) throws SQLException {
        Post post = new Post();
        post.setPostID(rs.getInt("PostID"));
        post.setAccountID(rs.getInt("AccountID"));
        post.setTitle(rs.getString("Title"));
        post.setDateCreated(rs.getString("DateCreated"));
        post.setPostbanner(rs.getString("Postbanner"));
        post.setContext(rs.getString("Context"));
        post.setDescription(rs.getString("Description"));
        post.setPostCategoryID(rs.getInt("PostCategoryID"));
        post.setViews(rs.getInt("Views"));
        return post;
    }
}
