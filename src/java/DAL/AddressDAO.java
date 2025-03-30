package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Model.AddressAnh;

public class AddressDAO extends DBContext {

    public List<AddressAnh> getAddressesByUserId(int userId) {
        List<AddressAnh> addresses = new ArrayList<>();
        String query = "SELECT address_id, name, phone, city, district, ward, street FROM Address WHERE user_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId); 
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AddressAnh address = new AddressAnh();
                    address.setAddressId(rs.getInt("address_id"));
                    address.setName(rs.getString("name"));
                    address.setPhone(rs.getString("phone"));
                    address.setCity(rs.getString("city"));
                    address.setDistrict(rs.getString("district"));
                    address.setWard(rs.getString("ward"));
                    address.setStreet(rs.getString("street"));

                    addresses.add(address);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        return addresses; 
    }
    
    
      public static void main(String[] args) {
        AddressDAO addressDAO = new AddressDAO();

        int userId = 5;

        List<AddressAnh> addresses = addressDAO.getAddressesByUserId(userId);

        if (addresses.isEmpty()) {
            System.out.println("Không tìm thấy địa chỉ nào cho userId: " + userId);
        } else {
            System.out.println("Danh sách địa chỉ của userId: " + userId);
            for (AddressAnh address : addresses) {
                System.out.println("Address ID: " + address.getAddressId());
                System.out.println("Name: " + address.getName());
                System.out.println("Phone: " + address.getPhone());
                System.out.println("City: " + address.getCity());
                System.out.println("District: " + address.getDistrict());
                System.out.println("Ward: " + address.getWard());
                System.out.println("Street: " + address.getStreet());
                System.out.println("-----------------------------");
            }
        }
    }
      
      public boolean addAddress(AddressAnh address, int userId) {
    String query = "INSERT INTO Address (name, phone, city, district, ward, street, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
        ps.setString(1, address.getName());
        ps.setString(2, address.getPhone());
        ps.setString(3, address.getCity());
        ps.setString(4, address.getDistrict());
        ps.setString(5, address.getWard());
        ps.setString(6, address.getStreet());
        ps.setInt(7, userId);
        return ps.executeUpdate() > 0; 
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false; // Trả về false nếu có lỗi
}
public boolean deleteAddress(int addressId) {
    String query = "DELETE FROM Address WHERE address_id = ?";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
        ps.setInt(1, addressId);
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false; 
}


public boolean updateAddress(AddressAnh address) {
    String query = "UPDATE Address SET name = ?, phone = ?, city = ?, district = ?, ward = ?, street = ? WHERE address_id = ?";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
        ps.setString(1, address.getName());
        ps.setString(2, address.getPhone());
        ps.setString(3, address.getCity());
        ps.setString(4, address.getDistrict());
        ps.setString(5, address.getWard());
        ps.setString(6, address.getStreet());
        ps.setInt(7, address.getAddressId());
        return ps.executeUpdate() > 0; 
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false; 
}

}
