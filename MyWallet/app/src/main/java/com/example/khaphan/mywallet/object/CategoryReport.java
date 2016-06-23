package com.example.khaphan.mywallet.object;

/**
 * Created by kha.phan on 6/23/2016.
 */
public class CategoryReport {
    private int idCategory;
    private int value;
    private String type;
    private String nameCategory;

    public CategoryReport(int idCategory, int value, String type, String nameCategory) {
        this.idCategory = idCategory;
        this.value = value;
        this.type = type;
        this.nameCategory = nameCategory;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    @Override
    public String toString() {
        return "CategoryReport{" +
                "idCategory=" + idCategory +
                ", value=" + value +
                ", type='" + type + '\'' +
                ", nameCategory='" + nameCategory + '\'' +
                '}';
    }
}
