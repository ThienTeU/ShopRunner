/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO.DucAnh;

import DTO.DucAnh.PostDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author 1234
 */
public class PostListDAO extends DBcontext {

    public ArrayList<PostDTO> getAllPost() {
        String sql = "select p.PostID, p.Title, p.DateCreated, p.Postbanner, p.Context, p.Description, c.Name, p.Views\n"
                + "                from Post p join PostCategory c\n"
                + "                on p.PostCategoryID = c.PostCategoryID";

        ArrayList<PostDTO> postDTOs = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                PostDTO postDTO = new PostDTO();
                postDTO.setPostID(rs.getInt("PostID"));
                postDTO.setTitle(rs.getString("Title"));
                postDTO.setPostImg(rs.getString("Postbanner"));
                postDTO.setDescription(rs.getString("Description"));
                postDTO.setDateCreated(rs.getString("DateCreated"));
                postDTO.setContext(rs.getString("Context"));
                postDTO.setCategory(rs.getString("Name"));
                postDTO.setViews(rs.getInt("Views"));
                postDTOs.add(postDTO);
            }

        } catch (SQLException e) {
            e.getMessage();
        }
        return postDTOs;
    }

    public PostDTO getPostDTOByID(int postID) {
        String sql = "select * from Post where PostID = ? ";
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
                postDTO.setViews(rs.getInt("Views"));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return postDTO;
    }

    public static void main(String[] args) {
        PostListDAO pldao = new PostListDAO();
        ArrayList<PostDTO> pdtos = pldao.getPostSearch("Xu Hướng");
        System.out.println(pdtos.size());

    }

    public ArrayList<PostDTO> getPostLatest(int postID) {
        String sql = "	select top(2) * from Post where PostID != ?\n"
                + "	order by DateCreated desc ";
        ArrayList<PostDTO> postDTOs = new ArrayList<>();

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, postID);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                PostDTO postDTO = new PostDTO();
                postDTO.setPostID(rs.getInt("PostID"));
                postDTO.setTitle(rs.getString("Title"));
                postDTO.setPostImg(rs.getString("Postbanner"));
                postDTO.setDescription(rs.getString("Description"));
                postDTO.setDateCreated(rs.getString("DateCreated"));
                postDTO.setContext(rs.getString("Context"));
                postDTO.setViews(rs.getInt("Views"));
                postDTOs.add(postDTO);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return postDTOs;
    }

    public ArrayList<PostDTO> getAllPostWithCondition(int num) {
        String sql = "";
        ArrayList<PostDTO> postDTOs = new ArrayList<>();
        if (num == 0) {
            sql = "select top(6)  p.PostID, p.Title, p.DateCreated, p.Postbanner, p.Context, p.Description, c.Name, p.Views from Post p \n"
                    + "join PostCategory c on p.PostCategoryID = c.PostCategoryID\n"
                    + "order by DateCreated desc";
        } else {
            sql = "select p.PostID, p.Title, p.DateCreated, p.Postbanner, p.Context, p.Description, c.Name, p.Views"
                    + " from "
                    + "Post p join PostCategory c on p.PostCategoryID = c.PostCategoryID\n"
                    + "	where c.PostCategoryID = ? order by DateCreated desc";
        }
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            if (num != 0) {
                st.setInt(1, num);
            }
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                PostDTO postDTO = new PostDTO();
                postDTO.setPostID(rs.getInt("PostID"));
                postDTO.setTitle(rs.getString("Title"));
                postDTO.setPostImg(rs.getString("Postbanner"));
                postDTO.setDescription(rs.getString("Description"));
                postDTO.setDateCreated(rs.getString("DateCreated"));
                postDTO.setContext(rs.getString("Context"));
                postDTO.setCategory(rs.getString("Name"));
                postDTO.setViews(rs.getInt("Views"));
                postDTOs.add(postDTO);
            }
            return postDTOs;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<PostDTO> getPostSearch(String searchKey) {
        String sql = "SELECT * FROM Post WHERE Title LIKE N'%' + ? + '%'";
        ArrayList<PostDTO> postDTOs = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, searchKey);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                PostDTO postDTO = new PostDTO();
                postDTO.setPostID(rs.getInt("PostID"));
                postDTO.setTitle(rs.getString("Title"));
                postDTO.setPostImg(rs.getString("Postbanner"));
                postDTO.setDescription(rs.getString("Description"));
                postDTO.setDateCreated(rs.getString("DateCreated"));
                postDTO.setContext(rs.getString("Context"));
                postDTO.setViews(rs.getInt("Views"));
                postDTOs.add(postDTO);
            }
            return postDTOs;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int addNewPost(PostDTO pdto) {
        PostCategoryDAO postCategoryDAO = new PostCategoryDAO();
        int categoryId = postCategoryDAO.getCategoryIDByName(pdto.getCategory());
        String sql = "INSERT INTO [dbo].[Post]\n"
                + "           ([AccountID],\n"
                + "            [Title]\n"
                + "           ,[DateCreated]\n"
                + "           ,[Postbanner]\n"
                + "           ,[Context]\n"
                + "           ,[PostCategoryID])\n"
                + "     VALUES\n"
                + "           (?,?,FORMAT(GETDATE(), 'yyyy-MM-dd'),?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, 1);
            ps.setString(2, pdto.getTitle());
            ps.setString(3, pdto.getPostImg());
            ps.setString(4, pdto.getContext());
            ps.setInt(5, categoryId);
            int checkAdd = ps.executeUpdate();
            return checkAdd;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public int actionWithPostById(int id, String action) {
        String sql = "";
        if (action.equals("hide")) {
            sql = "UPDATE Post SET Status = 0 WHERE PostID = ?";
        }
        if (action.equals("show")) {
            sql = "UPDATE Post SET Status = 1 WHERE PostID = ?";
        }
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            int check = st.executeUpdate();
            return check;
        } catch (SQLException e) {
            System.out.println("Error in actionWithPostById: " + e.getMessage());
        }
        return 0;
    }

    public int deletePostByID(int postID) {
        String sql = "delete Post where PostID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, postID);
            int checkDelete = ps.executeUpdate();
            return checkDelete;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public ArrayList<PostDTO> pagingPostWithCondition(int pcateID, int indexPage) {
        String sql = "select p.PostID, p.Title, p.DateCreated, p.Postbanner, p.Context, p.Description, c.Name\n"
                + "                from Post p join PostCategory c\n"
                + "                on p.PostCategoryID = c.PostCategoryID\n"
                + "                 where p.PostCategoryID = ?\n"
                + "                 ORDER BY p.DateCreated DESC\n"
                + "                 OFFSET ? ROWS FETCH NEXT 3 ROWS ONLY";
        ArrayList<PostDTO> postDTOs = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, (pcateID));
            st.setInt(2, (indexPage - 1) * 3);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                PostDTO postDTO = new PostDTO();
                postDTO.setPostID(rs.getInt("PostID"));
                postDTO.setTitle(rs.getString("Title"));
                postDTO.setPostImg(rs.getString("Postbanner"));
                postDTO.setDescription(rs.getString("Description"));
                postDTO.setDateCreated(rs.getString("DateCreated"));
                postDTO.setContext(rs.getString("Context"));
                postDTO.setCategory(rs.getString("Name"));
                postDTOs.add(postDTO);
            }

        } catch (SQLException e) {
            e.getMessage();
        }
        return postDTOs;
    }

    public ArrayList<PostDTO> pagingPostWithSearch(int page, int pageSize) {
        String sql = "SELECT p.PostID, p.Title, p.DateCreated, p.Postbanner, p.Context, p.Description, c.Name "
                + "FROM Post p "
                + "JOIN PostCategory c ON p.PostCategoryID = c.PostCategoryID "
                + "ORDER BY p.DateCreated DESC "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        ArrayList<PostDTO> postDTOs = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);

            // Tìm kiếm theo từ khóa trong Title và Description

            // Tính toán OFFSET và FETCH
            st.setInt(1, (page - 1) * pageSize);  // OFFSET
            st.setInt(2, pageSize);  // FETCH NEXT

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                PostDTO postDTO = new PostDTO();
                postDTO.setPostID(rs.getInt("PostID"));
                postDTO.setTitle(rs.getString("Title"));
                postDTO.setPostImg(rs.getString("Postbanner"));
                postDTO.setDescription(rs.getString("Description"));
                postDTO.setDateCreated(rs.getString("DateCreated"));
                postDTO.setContext(rs.getString("Context"));
                postDTO.setCategory(rs.getString("Name"));
                postDTOs.add(postDTO);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postDTOs;
    }
    
    public int getTotalPosts() {
    String sql = "SELECT COUNT(*) FROM Post";
    try {
        PreparedStatement st = connection.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            return rs.getInt(1); // Trả về tổng số bài viết
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;
}


    public ArrayList<PostDTO> pagingPost(int indexPage) {
        String sql = "select p.PostID, p.Title, p.DateCreated, p.Postbanner, p.Context, p.Description, c.Name\n"
                + "                from Post p join PostCategory c\n"
                + "                on p.PostCategoryID = c.PostCategoryID\n"
                + "                ORDER BY p.DateCreated DESC\n"
                + "OFFSET ? ROWS FETCH NEXT 3 ROWS ONLY";
        ArrayList<PostDTO> postDTOs = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, (indexPage - 1) * 3);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                PostDTO postDTO = new PostDTO();
                postDTO.setPostID(rs.getInt("PostID"));
                postDTO.setTitle(rs.getString("Title"));
                postDTO.setPostImg(rs.getString("Postbanner"));
                postDTO.setDescription(rs.getString("Description"));
                postDTO.setDateCreated(rs.getString("DateCreated"));
                postDTO.setContext(rs.getString("Context"));
                postDTO.setCategory(rs.getString("Name"));
                postDTOs.add(postDTO);
            }

        } catch (SQLException e) {
            e.getMessage();
        }
        return postDTOs;
    }

}
