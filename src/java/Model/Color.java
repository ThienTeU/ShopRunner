/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author admin
 */
public class Color {
    private int color_id;
    private String color;

    public Color() {
    }

    public Color(int color_id, String color) {
        this.color_id = color_id;
        this.color = color;
    }

    public int getColor_id() {
        return color_id;
    }

    public void setColor_id(int color_id) {
        this.color_id = color_id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Color{");
        sb.append("color_id=").append(color_id);
        sb.append(", color=").append(color);
        sb.append('}');
        return sb.toString();
    }
    
}
