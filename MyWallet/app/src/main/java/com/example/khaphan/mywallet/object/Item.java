package com.example.khaphan.mywallet.object;

import java.io.Serializable;

/**
 * Created by kha.phan on 6/21/2016.
 */
public class Item implements Serializable {
    private int idItem;
    private String typeItem;
    private String nameItem;
    private String date;
    private String value;
    private int idCategory;

    public Item(int idItem, String typeItem, String nameItem, String date, String value, int idCategory) {
        this.idItem = idItem;
        this.typeItem = typeItem;
        this.nameItem = nameItem;
        this.date = date;
        this.value = value;
        this.idCategory = idCategory;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public String getTypeItem() {
        return typeItem;
    }

    public void setTypeItem(String typeItem) {
        this.typeItem = typeItem;
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    @Override
    public String toString() {
        return "Item{" +
                "idItem=" + idItem +
                ", typeItem=" + typeItem +
                ", nameItem='" + nameItem + '\'' +
                ", date='" + date + '\'' +
                ", value=" + value +
                ", idCategory=" + idCategory +
                '}';
    }
}
