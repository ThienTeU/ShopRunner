/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Model.Banner;
import Model.Blog;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class BlogDAO extends DBContext {

    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<Blog> getAllBlogPosts() throws SQLException {
        List<Blog> list = new ArrayList<>();
        String sql = "SELECT * FROM Blog";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Blog blog = new Blog(
                        rs.getInt("blog_id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("author"),
                        rs.getString("created_at"),
                        rs.getBoolean("status"),
                        rs.getString("thumbnail")
                );
                list.add(blog);
            }
        }
        return list;
    }

    public List<Banner> getAllBanners() throws SQLException {
        List<Banner> list = new ArrayList<>();
        String sql = "SELECT * FROM Banner";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Banner banner = new Banner(
                        rs.getInt("banner_id"),
                        rs.getString("image_url"),
                        rs.getString("link_url"),
                        rs.getInt("display_order"),
                        rs.getBoolean("status")
                );
                list.add(banner);
            }
        }
        return list;
    }

}
