package com.example.cheapweb;

public class Model {

    String idItem, imagePath, itemName, itemPrice, itemInfo, link1, link2 ,link3, priceInLink1, priceInLink2, priceInLink3;
    private int isLiked;

    public Model(){

    }

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(String itemInfo) {
        this.itemInfo = itemInfo;
    }

    public String getLink1() {
        return link1;
    }

    public void setLink1(String link1) {
        this.link1 = link1;
    }

    public String getLink2() {
        return link2;
    }

    public void setLink2(String link2) {
        this.link2 = link2;
    }

    public String getLink3() {
        return link3;
    }

    public void setLink3(String link3) {
        this.link3 = link3;
    }

    public String getPriceInLink1() {
        return priceInLink1;
    }

    public void setPriceInLink1(String priceInLink1) {
        this.priceInLink1 = priceInLink1;
    }

    public String getPriceInLink2() {
        return priceInLink2;
    }

    public void setPriceInLink2(String priceInLink2) {
        this.priceInLink2 = priceInLink2;
    }

    public String getPriceInLink3() {
        return priceInLink3;
    }

    public void setPriceInLink3(String priceInLink3) {
        this.priceInLink3 = priceInLink3;
    }

    public int getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(int isLiked) {
        this.isLiked = isLiked;
    }
}
