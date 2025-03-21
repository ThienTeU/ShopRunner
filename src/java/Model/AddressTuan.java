/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author tuan
 */
public class AddressTuan {
    private int addressId;
    private int userId;
    private String name;
    private String phone;
    private String city;
    private String district;
    private String ward;
    private String street;

    public AddressTuan() {
    }

    public AddressTuan(int addressId, int userId, String name, String phone, String city, String district, String ward, String street) {
        this.addressId = addressId;
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.city = city;
        this.district = district;
        this.ward = ward;
        this.street = street;
    }
    
    public AddressTuan( int userId, String name, String phone, String city, String district, String ward, String street) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.city = city;
        this.district = district;
        this.ward = ward;
        this.street = street;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "AddressTuan{" + "addressId=" + addressId + ", userId=" + userId + ", name=" + name + ", phone=" + phone + ", city=" + city + ", district=" + district + ", ward=" + ward + ", street=" + street + '}';
    }
}
