package com.example.easyfood.data.pojo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "mealInformation")
public class Meal {

    private Date dateModified;
    private String strIngredient10;
    private String strIngredient12;
    private String strIngredient11;
    private String strIngredient14;
    private String strCategory;
    private String strIngredient13;
    private String strIngredient16;
    private String strIngredient15;
    private String strIngredient18;
    private String strIngredient17;
    private String strArea;
    private String strIngredient19;
    private String strTags;

    @PrimaryKey
    @NonNull
    private String idMeal;
    private String strInstructions;
    private String strIngredient1;
    private String strIngredient3;
    private String strIngredient2;
    private String strIngredient20;
    private String strIngredient5;
    private String strIngredient4;
    private String strIngredient7;
    private String strIngredient6;
    private String strIngredient9;
    private String strIngredient8;
    private String strMealThumb;
    private String strMeasure20;
    private String strYoutube;
    private String strMeal;
    private String strMeasure12;
    private String strMeasure13;
    private String strMeasure10;
    private String strMeasure11;
    private String strSource;
    private String strMeasure9;
    private String strMeasure7;
    private String strMeasure8;
    private String strMeasure5;
    private String strMeasure6;
    private String strMeasure3;
    private String strMeasure4;
    private String strMeasure1;
    private String strMeasure18;
    private String strMeasure2;
    private String strMeasure19;
    private String strMeasure16;
    private String strMeasure17;
    private String strMeasure14;
    private String strMeasure15;

    public Date getDateModified() {
        return dateModified;
    }
    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public String getStrIngredient10() { return strIngredient10; }
    public void setStrIngredient10(String value) { this.strIngredient10 = value; }

    public String getStrIngredient12() { return strIngredient12; }
    public void setStrIngredient12(String value) { this.strIngredient12 = value; }

    public String getStrIngredient11() { return strIngredient11; }
    public void setStrIngredient11(String value) { this.strIngredient11 = value; }

    public String getStrIngredient14() { return strIngredient14; }
    public void setStrIngredient14(String value) { this.strIngredient14 = value; }

    public String getStrCategory() { return strCategory; }
    public void setStrCategory(String value) { this.strCategory = value; }

    public String getStrIngredient13() { return strIngredient13; }
    public void setStrIngredient13(String value) { this.strIngredient13 = value; }

    public String getStrIngredient16() { return strIngredient16; }
    public void setStrIngredient16(String value) { this.strIngredient16 = value; }

    public String getStrIngredient15() { return strIngredient15; }
    public void setStrIngredient15(String value) { this.strIngredient15 = value; }

    public String getStrIngredient18() { return strIngredient18; }
    public void setStrIngredient18(String value) { this.strIngredient18 = value; }

    public String getStrIngredient17() { return strIngredient17; }
    public void setStrIngredient17(String value) { this.strIngredient17 = value; }

    public String getStrArea() { return strArea; }
    public void setStrArea(String value) { this.strArea = value; }

    public String getStrIngredient19() { return strIngredient19; }
    public void setStrIngredient19(String value) { this.strIngredient19 = value; }

    public String getStrTags() { return strTags; }
    public void setStrTags(String value) { this.strTags = value; }

    public String getIdMeal() { return idMeal; }
    public void setIdMeal(String value) { this.idMeal = value; }

    public String getStrInstructions() { return strInstructions; }
    public void setStrInstructions(String value) { this.strInstructions = value; }

    public String getStrIngredient1() { return strIngredient1; }
    public void setStrIngredient1(String value) { this.strIngredient1 = value; }

    public String getStrIngredient3() { return strIngredient3; }
    public void setStrIngredient3(String value) { this.strIngredient3 = value; }

    public String getStrIngredient2() { return strIngredient2; }
    public void setStrIngredient2(String value) { this.strIngredient2 = value; }

    public String getStrIngredient20() { return strIngredient20; }
    public void setStrIngredient20(String value) { this.strIngredient20 = value; }

    public String getStrIngredient5() { return strIngredient5; }
    public void setStrIngredient5(String value) { this.strIngredient5 = value; }

    public String getStrIngredient4() { return strIngredient4; }
    public void setStrIngredient4(String value) { this.strIngredient4 = value; }

    public String getStrIngredient7() { return strIngredient7; }
    public void setStrIngredient7(String value) { this.strIngredient7 = value; }

    public String getStrIngredient6() { return strIngredient6; }
    public void setStrIngredient6(String value) { this.strIngredient6 = value; }

    public String getStrIngredient9() { return strIngredient9; }
    public void setStrIngredient9(String value) { this.strIngredient9 = value; }

    public String getStrIngredient8() { return strIngredient8; }
    public void setStrIngredient8(String value) { this.strIngredient8 = value; }

    public String getStrMealThumb() { return strMealThumb; }
    public void setStrMealThumb(String value) { this.strMealThumb = value; }

    public String getStrMeasure20() { return strMeasure20; }
    public void setStrMeasure20(String value) { this.strMeasure20 = value; }

    public String getStrYoutube() { return strYoutube; }
    public void setStrYoutube(String value) { this.strYoutube = value; }

    public String getStrMeal() { return strMeal; }
    public void setStrMeal(String value) { this.strMeal = value; }

    public String getStrMeasure12() { return strMeasure12; }
    public void setStrMeasure12(String value) { this.strMeasure12 = value; }

    public String getStrMeasure13() { return strMeasure13; }
    public void setStrMeasure13(String value) { this.strMeasure13 = value; }

    public String getStrMeasure10() { return strMeasure10; }
    public void setStrMeasure10(String value) { this.strMeasure10 = value; }

    public String getStrMeasure11() { return strMeasure11; }
    public void setStrMeasure11(String value) { this.strMeasure11 = value; }

    public String getStrSource() { return strSource; }
    public void setStrSource(String value) { this.strSource = value; }

    public String getStrMeasure9() { return strMeasure9; }
    public void setStrMeasure9(String value) { this.strMeasure9 = value; }

    public String getStrMeasure7() { return strMeasure7; }
    public void setStrMeasure7(String value) { this.strMeasure7 = value; }

    public String getStrMeasure8() { return strMeasure8; }
    public void setStrMeasure8(String value) { this.strMeasure8 = value; }

    public String getStrMeasure5() { return strMeasure5; }
    public void setStrMeasure5(String value) { this.strMeasure5 = value; }

    public String getStrMeasure6() { return strMeasure6; }
    public void setStrMeasure6(String value) { this.strMeasure6 = value; }

    public String getStrMeasure3() { return strMeasure3; }
    public void setStrMeasure3(String value) { this.strMeasure3 = value; }

    public String getStrMeasure4() { return strMeasure4; }
    public void setStrMeasure4(String value) { this.strMeasure4 = value; }

    public String getStrMeasure1() { return strMeasure1; }
    public void setStrMeasure1(String value) { this.strMeasure1 = value; }

    public String getStrMeasure18() { return strMeasure18; }
    public void setStrMeasure18(String value) { this.strMeasure18 = value; }

    public String getStrMeasure2() { return strMeasure2; }
    public void setStrMeasure2(String value) { this.strMeasure2 = value; }

    public String getStrMeasure19() { return strMeasure19; }
    public void setStrMeasure19(String value) { this.strMeasure19 = value; }

    public String getStrMeasure16() { return strMeasure16; }
    public void setStrMeasure16(String value) { this.strMeasure16 = value; }

    public String getStrMeasure17() { return strMeasure17; }
    public void setStrMeasure17(String value) { this.strMeasure17 = value; }

    public String getStrMeasure14() { return strMeasure14; }
    public void setStrMeasure14(String value) { this.strMeasure14 = value; }

    public String getStrMeasure15() { return strMeasure15; }
    public void setStrMeasure15(String value) { this.strMeasure15 = value; }
}
