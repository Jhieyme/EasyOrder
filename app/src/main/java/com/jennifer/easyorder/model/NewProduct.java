package com.jennifer.easyorder.model;

import java.io.Serializable;
import java.util.Objects;

public class NewProduct implements Serializable {


    private Product product;
    private int quantity;

    public NewProduct(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewProduct that = (NewProduct) o;
        return Objects.equals(product.getIdProducto(), that.product.getIdProducto());
    }

    @Override
    public int hashCode() {
        return Objects.hash(product.getIdProducto());
    }


}
