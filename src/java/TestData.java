
import Controller.ProductDetailServlet;
import DAL.ProductDAO;
import Model.Category;
import Model.Color;
import Model.Product;
import Model.ProductImage;
import Model.ProductPrice;
import Model.ProductQuantity;
import Model.Size;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author admin
 */
public class TestData {
    public static void main(String[] args) {
        try {
            int product_id = 1;
            
            ProductDAO productDao = new ProductDAO();
            List<Color> listColor = productDao.getAllColors();
            List<Size> listSize = productDao.getAllSizes();
            
            List<Category> listCategory = productDao.getAllCategories();
            List<Product> listProduct = productDao.getAllProduct();
            
            
            
            List<ProductPrice> listProductPrice = productDao.getProductPricesByProductId(product_id);
            
            int ProductPrice_ID = listProductPrice.get(0).getProductPrice_id();
            int color_id = listProductPrice.get(0).getColor_id();
            
            List<ProductImage> listProductImage = productDao.getProductImagesByProductIdAndColorId(product_id, color_id);
            for(ProductImage pi : listProductImage){
                System.out.println(pi.toString());
            }

            for(ProductPrice pp : listProductPrice){
                System.out.println(pp.toString());
            }
            
            System.out.println(ProductPrice_ID);
            
            List<ProductQuantity> listProductQuantity = productDao.getProductQuantitiesByProductPriceId(ProductPrice_ID);
            
            for(ProductQuantity pq : listProductQuantity){
                System.out.println(pq.toString());
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductDetailServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
