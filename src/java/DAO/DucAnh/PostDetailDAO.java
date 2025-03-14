/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.DucAnh;

import DTO.DucAnh.PostDTO;
import DTO.DucAnh.PostDetailDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author 1234
 */
public class PostDetailDAO extends DBcontext {

    public PostDTO getPostDTOByID(int postID) {
        String sql = "select p.PostID,p.Title, p.Postbanner,p.DateCreated,p.Context, c.Name, p.Description, p.Views\n"
                + "from Post p join PostCategory c\n"
                + "on p.PostCategoryID = c.PostCategoryID \n"
                + "where p.PostID = ?";
        PostDTO postDTO = new PostDTO();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, postID);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                postDTO.setPostID(rs.getInt("PostID"));
                postDTO.setTitle(rs.getString("Title"));
                postDTO.setPostImg(rs.getString("Postbanner"));
                postDTO.setDescription(rs.getString("Description"));
                postDTO.setDateCreated(rs.getString("DateCreated"));
                postDTO.setContext(rs.getString("Context"));
                postDTO.setCategory(rs.getString("Name"));
                postDTO.setViews(rs.getInt("Views"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return postDTO;
    }

    public int updatePost(PostDTO pdto) {
        String sql = "UPDATE [dbo].[Post]\n"
                + "   SET [Title] = ?     \n"
                + "      ,[Postbanner] = ?\n"
                + "      ,[Context] = ?\n"
                + " WHERE PostID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, pdto.getTitle());
            ps.setString(2, pdto.getPostImg());
            ps.setString(3, pdto.getContext());
            ps.setInt(4, pdto.getPostID());
            int checkAdd = ps.executeUpdate();
            return checkAdd;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public void updatePostView(int PostID) throws SQLException {
        String sql = "UPDATE Post SET [Views] = [Views] + 1 WHERE PostID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, PostID); // Gán giá trị view_id vào câu lệnh SQL
            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("✅ Cập nhật thành công prduct_id = " + PostID);
            } else {
                System.out.println("⚠️ Không tìm thấy product_id = " + PostID);
            }
        }
    }
            
public int getPostViews(int postID) throws SQLException {
    String sql = "SELECT [Views] FROM Post WHERE PostID = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, postID);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("Views"); // Lấy giá trị số lượt xem
            }
        }
    }
    return 0; // Trả về 0 nếu không tìm thấy bài viết
}
    
    
    public static void main(String[] args) {
        PostDetailDAO p = new PostDetailDAO();
        PostDTO pdto = p.getPostDTOByID(1);
        System.out.println(pdto.getCategory());
    }

}
