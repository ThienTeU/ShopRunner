/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author admin
 */
public class Size {
    private int size_id;
    private String size;

    public Size() {
    }

    public Size(int size_id, String size) {
        this.size_id = size_id;
        this.size = size;
    }

    public int getSize_id() {
        return size_id;
    }

    public void setSize_id(int size_id) {
        this.size_id = size_id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Size{");
        sb.append("size_id=").append(size_id);
        sb.append(", size=").append(size);
        sb.append('}');
        return sb.toString();
    }
    
}
