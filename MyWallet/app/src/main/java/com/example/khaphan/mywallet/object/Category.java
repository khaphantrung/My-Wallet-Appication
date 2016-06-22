package com.example.khaphan.mywallet.object;

import java.io.Serializable;

/**
 * Created by kha.phan on 6/21/2016.
 */
public class Category implements Serializable {
    private int idCategory;
    private String nameCategory;
    private String iconName;

    public Category(int idCatelory, String nameCatelory, String iconName) {
        this.idCategory = idCatelory;
        this.nameCategory = nameCatelory;
        this.iconName = iconName;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    @Override
    public String toString() {
        return "Category{" +
                "idCategory=" + idCategory +
                ", nameCategory='" + nameCategory + '\'' +
                ", iconName='" + iconName + '\'' +
                '}';
    }
}
