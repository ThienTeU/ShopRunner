/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tuan
 */
public class CategoryTuan {

    private int categoryId;
    private String name;
    private Integer parentId; // Có thể null nếu là danh mục gốc
    private List<CategoryTuan> children = new ArrayList<>();

    public CategoryTuan() {
    }

    public CategoryTuan(int categoryId, String name, Integer parentId) {
        this.categoryId = categoryId;
        this.name = name;
        this.parentId = parentId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<CategoryTuan> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryTuan> children) {
        this.children = children;
    }

}
