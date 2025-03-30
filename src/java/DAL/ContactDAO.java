package DAL;

import Model.Contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContactDAO extends DBContext {
    private static final Logger LOGGER = Logger.getLogger(ContactDAO.class.getName());

    public boolean saveContact(Contact contact) {
        String query = "INSERT INTO Contact (full_name, phone, email, city, content) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, contact.getFullName());
            ps.setString(2, contact.getPhone());
            ps.setString(3, contact.getEmail());
            ps.setString(4, contact.getCity());
            ps.setString(5, contact.getContent());
            return ps.executeUpdate() > 0; // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error while saving contact: {0}", e.getMessage());
            return false;
        }
    }

    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        String query = "SELECT contact_id, full_name, phone, email, city, content, created_at, status FROM Contact";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Contact contact = new Contact();
                contact.setContactId(rs.getInt("contact_id"));
                contact.setFullName(rs.getString("full_name"));
                contact.setPhone(rs.getString("phone"));
                contact.setEmail(rs.getString("email"));
                contact.setCity(rs.getString("city"));
                contact.setContent(rs.getString("content"));
                contact.setCreatedAt(rs.getTimestamp("created_at"));
                contact.setStatus(rs.getBoolean("status"));
                contacts.add(contact);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error while retrieving contacts: {0}", e.getMessage());
        }
        return contacts;
    }

    public boolean deleteContact(int contactId) {
        String query = "DELETE FROM Contact WHERE contact_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, contactId);
            return ps.executeUpdate() > 0; // Trả về true nếu xóa thành công
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error while deleting contact: {0}", e.getMessage());
            return false;
        }
    }

    public boolean updateContact(Contact contact) {
        String query = "UPDATE Contact SET full_name = ?, phone = ?, email = ?, city = ?, content = ? WHERE contact_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, contact.getFullName());
            ps.setString(2, contact.getPhone());
            ps.setString(3, contact.getEmail());
            ps.setString(4, contact.getCity());
            ps.setString(5, contact.getContent());
            ps.setInt(6, contact.getContactId());
            return ps.executeUpdate() > 0; 
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error while updating contact: {0}", e.getMessage());
            return false;
        }
    }

    public Contact getContactById(int contactId) {
        String query = "SELECT contact_id, full_name, phone, email, city, content, created_at, status FROM Contact WHERE contact_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, contactId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Contact contact = new Contact();
                    contact.setContactId(rs.getInt("contact_id"));
                    contact.setFullName(rs.getString("full_name"));
                    contact.setPhone(rs.getString("phone"));
                    contact.setEmail(rs.getString("email"));
                    contact.setCity(rs.getString("city"));
                    contact.setContent(rs.getString("content"));
                    contact.setCreatedAt(rs.getTimestamp("created_at"));
                    contact.setStatus(rs.getBoolean("status"));
                    return contact;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error while retrieving contact by ID: {0}", e.getMessage());
        }
        return null;
    }
    
public void updateContactStatus(int contactId, boolean status) {
    String sql = "UPDATE Contact SET status = ? WHERE contact_id = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setBoolean(1, status);
        stmt.setInt(2, contactId);
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public List<Contact> getContacts(int offset, int limit) {
    List<Contact> contacts = new ArrayList<>();
    String query = "SELECT contact_id, full_name, phone, email, city, content, created_at, status FROM Contact LIMIT ? OFFSET ?";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
        ps.setInt(1, limit);
        ps.setInt(2, offset);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Contact contact = new Contact();
                contact.setContactId(rs.getInt("contact_id"));
                contact.setFullName(rs.getString("full_name"));
                contact.setPhone(rs.getString("phone"));
                contact.setEmail(rs.getString("email"));
                contact.setCity(rs.getString("city"));
                contact.setContent(rs.getString("content"));
                contact.setCreatedAt(rs.getTimestamp("created_at"));
                contact.setStatus(rs.getBoolean("status"));
                contacts.add(contact);
            }
        }
    } catch (SQLException e) {
        LOGGER.log(Level.SEVERE, "Error while retrieving contacts with pagination: {0}", e.getMessage());
    }
    return contacts;
}
public int getTotalRecords() {
    String query = "SELECT COUNT(*) FROM Contact";
    try (PreparedStatement ps = connection.prepareStatement(query);
         ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
            return rs.getInt(1); 
        }
    } catch (SQLException e) {
        LOGGER.log(Level.SEVERE, "Error while retrieving total records: {0}", e.getMessage());
    }
    return 0;
}


}
