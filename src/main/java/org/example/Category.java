package org.example;

public class Category {
    private int categoryID;
    private String categoryName;
    private CategoryType categoryType;

    public enum CategoryType {
        INCOME,
        EXPENSE
    }


    public Category(int categoryID, String categoryName, CategoryType categoryType) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    // Getters and setters

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }
}

