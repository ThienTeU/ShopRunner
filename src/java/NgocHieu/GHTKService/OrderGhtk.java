/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NgocHieu.GHTKService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author admin
 */
public class OrderGhtk {
    private String id;
    private String pick_name;
    private String pick_address;
    private String pick_province;
    private String pick_district;
    private String pick_ward;
    private String pick_tel;
    private String tel;
    private String name;
    private String address;
    private String province;
    private String district;
    private String ward;
    private String hamlet;
    private String is_freeship;
    private String pick_date;
    private int pick_money;
    private String note;
    private int value;
    private String transport;
    private String pick_option;
    private String deliver_option;

    public OrderGhtk(String id, String tel, String name, String address,
                 String province, String district, String ward,int pick_money, int value) {
        this.id = id;
        this.pick_name = "HN-nội thành";
        this.pick_address = "Đại Học FPT";
        this.pick_province = "TP. Hà Nội";
        this.pick_district = "Huyện Thạch Thất";
        this.pick_ward = "Xã Bình Yên";
        this.pick_tel = "0397761602";
        this.tel = tel;
        this.name = name;
        this.address = address;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.hamlet = "Khác";
        this.is_freeship = "0";
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formatDate = now.format(formatter);
        this.pick_date = formatDate;
        this.pick_money = pick_money;
        this.value = value;
        this.transport = "road";
        this.pick_option = "cod";
        this.deliver_option = "6h";
    }
    
    public OrderGhtk(){
        
    }

    public String getId() { return id; }
    public String getPick_name() { return pick_name; }
    public String getPick_address() { return pick_address; }
    public String getPick_province() { return pick_province; }
    public String getPick_district() { return pick_district; }
    public String getPick_ward() { return pick_ward; }
    public String getPick_tel() { return pick_tel; }
    public String getTel() { return tel; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getProvince() { return province; }
    public String getDistrict() { return district; }
    public String getWard() { return ward; }
    public String getHamlet() { return hamlet; }
    public String getIs_freeship() { return is_freeship; }
    public String getPick_date() { return pick_date; }
    public int getPick_money() { return pick_money; }
    public String getNote() { return note; }
    public int getValue() { return value; }
    public String getTransport() { return transport; }
    public String getPick_option() { return pick_option; }
    public String getDeliver_option() { return deliver_option; }

    public void setId(String id) {
        this.id = id;
    }

    public void setPick_money(int pick_money) {
        this.pick_money = pick_money;
    }
    
}

