/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NgocHieu.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EnvConfig {
    private static Properties properties = new Properties();

    static {
        try {
            // Xác định đường dẫn file .env
            String path = "C:/Users/admin/ShopRunner/config.env";

            FileInputStream fileInputStream = new FileInputStream(path);
            properties.load(fileInputStream);
        } catch (IOException e) {
            System.err.println("Lỗi: Không thể đọc file .env");
        }
    }

    // Hàm lấy giá trị từ .env
    public static String get(String key) {
        return properties.getProperty(key);
    }
    
    public static void main(String[] args) {
        System.out.println(get("SIGN_KEY"));
    }
}

