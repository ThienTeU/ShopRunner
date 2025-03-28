package DAL;

import Model.Favorite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FavoriteDAO extends DBContext {

    public boolean addFavorite(int userId, int productId) throws SQLException {
        String query = "INSERT INTO Favorite (user_id, product_id) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean removeFavorite(int userId, int productId) throws SQLException {
        String query = "DELETE FROM Favorite WHERE user_id = ? AND product_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Favorite> getFavoritesByUser(int userId) throws SQLException {
        List<Favorite> favorites = new ArrayList<>();
        String query = "SELECT * FROM Favorite WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Favorite favorite = new Favorite(
                            rs.getInt("favorite_id"),
                            rs.getInt("user_id"),
                            rs.getInt("product_id"),
                            rs.getTimestamp("created_at")
                    );
                    favorites.add(favorite);
                }
            }
        }
        return favorites;
    }

    public boolean isFavorite(int userId, int productId) throws SQLException {
        String query = "SELECT COUNT(*) FROM Favorite WHERE user_id = ? AND product_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}
