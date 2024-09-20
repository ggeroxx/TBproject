package Request;

import java.io.Serializable;

public class SomeRequestCategory implements Serializable {
    private static final long serialVersionUID = 1L;
    private String action;
    private int id;
    private String categoryName;
    private String field;
    private String description;
    private boolean isRoot;
    private int hierarchyID;

    public SomeRequestCategory(String action, int id, String categoryName, String field, String description, boolean isRoot, int hierarchyID) 
    {
        this.action = action;
        this.id = id;
        this.categoryName = categoryName;
        this.field = field;
        this.description = description;
        this.isRoot = isRoot;
        this.hierarchyID = hierarchyID;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getID()
    {
        return this.id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getField() {
        return this.field;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean getIsRoot() {
        return this.isRoot;
    }

    public int getHierarchyID()
    {
        return this.hierarchyID;
    }

    @Override
    public String toString() {
        return "SomeRequestCategory{" +
                "action='" + action + '\'' +
                ", id ='" + id + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", field='" + field + '\'' +
                ", description='" + description + '\'' +
                ", isRoot='" + isRoot + '\'' +
                ", hierarchyID='" + hierarchyID + '\'' +
                '}';
    }
    
}
