package com.ohgiraffers.pos.menu.dto;

public class MenuDTO {
    private int code;
    private String name;
    private int price;
    private int categoryCode;
    private String status;

    public MenuDTO() {
    }

    public MenuDTO(int code, String name, int price, int categoryCode, String status) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.categoryCode = categoryCode;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MenuDTO{" +
                "Code=" + code +
                ", Name='" + name + '\'' +
                ", Price=" + price +
                ", categoryCode=" + categoryCode +
                ", status='" + status + '\'' +
                '}';
    }
}
