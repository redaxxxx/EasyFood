
package com.example.easyfood.data.pojo;

import java.util.List;

public class MealsByCategoryList {
    private List<MealsByCategory> meals;

    public List<MealsByCategory> getMeals() { return meals; }
    public void setMeals(List<MealsByCategory> value) { this.meals = value; }

    public static class MealsByCategory {
        private String strMealThumb;
        private String idMeal;
        private String strMeal;

        public String getStrMealThumb() { return strMealThumb; }
        public void setStrMealThumb(String value) { this.strMealThumb = value; }

        public String getidMeal() { return idMeal; }
        public void setidMeal(String value) { this.idMeal = value; }

        public String getStrMeal() { return strMeal; }
        public void setStrMeal(String value) { this.strMeal = value; }
    }
}
