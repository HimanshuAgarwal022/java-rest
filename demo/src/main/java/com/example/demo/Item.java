package com.example.demo;

import java.util.Objects;

import javax.validation.constraints.NotNull;

public class Item {

    @NotNull
    private int productId;
    @NotNull
    private String productName;
    @NotNull
    private int quantity;
    @NotNull
    private int userId;

    public Item() {
        super();
    }

    public Item(int productId, String productName, int quantity, int userId) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.userId = userId;
    }

    public int getProductId() {
        return this.productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Item productId(int productId) {
        setProductId(productId);
        return this;
    }

    public Item productName(String productName) {
        setProductName(productName);
        return this;
    }

    public Item quantity(int quantity) {
        setQuantity(quantity);
        return this;
    }

    public Item userId(int userId) {
        setUserId(userId);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Item)) {
            return false;
        }
        Item item = (Item) o;
        return productId == item.productId && Objects.equals(productName, item.productName) && quantity == item.quantity && userId == item.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, quantity, userId);
    }

    @Override
    public String toString() {
        return "{" +
            " productId='" + getProductId() + "'" +
            ", productName='" + getProductName() + "'" +
            ", quantity='" + getQuantity() + "'" +
            ", userId='" + getUserId() + "'" +
            "}";
    }

    
}
