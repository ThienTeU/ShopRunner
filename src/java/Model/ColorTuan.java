package Model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author tuan
 */
public class ColorTuan {
    private String colorName;

    public ColorTuan() {
    }

    public ColorTuan(String colorName) {
        this.colorName = colorName;
    }
    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Color{");
        sb.append("colorName=").append(colorName);
        sb.append('}');
        return sb.toString();
    }

    
}
