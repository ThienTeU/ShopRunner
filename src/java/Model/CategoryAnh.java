package Model;

public class CategoryAnh {
    private int id;
    private String name;
    private Integer parentId;
    private String categoryPath;

    // Constructor đầy đủ
    public CategoryAnh(int id, String name, Integer parentId, String categoryPath) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.categoryPath = categoryPath;
    }

    // Constructor không có categoryPath
    public CategoryAnh(int id, String name, Integer parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.categoryPath = ""; // Mặc định là chuỗi rỗng
    }

    // Getter methods
    public int getId() { return id; }
    public String getName() { return name; }
    public Integer getParentId() { return parentId; }
    public String getCategoryPath() { return categoryPath; }
}
