package com.example.sisteminformasikliniik.Model;

public class BaseMenu {

    private String nameMenu;
    private int imgMenu;

    /*public BaseMenu(String nameMenu) {
        this.nameMenu = nameMenu;
    }*/

    public BaseMenu(String nameMenu, int imgMenu) {
        this.nameMenu = nameMenu;
        this.imgMenu = imgMenu;
    }

    public String getNameMenu() {
        return nameMenu;
    }

    public void setNameMenu(String nameMenu) {
        this.nameMenu = nameMenu;
    }

    public int getImgMenu() {
        return imgMenu;
    }

    public void setImgMenu(int imgMenu) {
        this.imgMenu = imgMenu;
    }
}
