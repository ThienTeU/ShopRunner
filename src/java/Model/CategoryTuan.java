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

    private int id;
    private String name;
    private Integer parentId;
    private List<CategoryTuan> children = new ArrayList<>();

    public CategoryTuan() {
    }

    public CategoryTuan(int id, String name, Integer parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CategoryTuan{");
        sb.append("id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", parentId=").append(parentId);
        sb.append(", children=").append(children);
        sb.append('}');
        return sb.toString();
    }

    
}
