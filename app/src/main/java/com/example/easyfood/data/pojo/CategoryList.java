
package com.example.easyfood.data.pojo;

import java.util.List;

public class CategoryList {
    private List<Category> categories;

    public List<Category> getCategories() { return categories; }
    public void setCategories(List<Category> value) { this.categories = value; }

    public static class Category {
        private String strCategory;
        private String strCategoryDescription;
        private String idCategory;
        private String strCategoryThumb;

        public String getStrCategory() { return strCategory; }
        public void setStrCategory(String value) { this.strCategory = value; }

        public String getStrCategoryDescription() { return strCategoryDescription; }
        public void setStrCategoryDescription(String value) { this.strCategoryDescription = value; }

        public String getidCategory() { return idCategory; }
        public void setidCategory(String value) { this.idCategory = value; }

        public String getStrCategoryThumb() { return strCategoryThumb; }
        public void setStrCategoryThumb(String value) { this.strCategoryThumb = value; }
    }
}
