package com.jennifer.easyorder.model;

import java.io.Serializable;

public class NewProduct  {


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
  public String toString() {
    return "Desde el metodo toString: " + this.product.getNombre() + "\nLa cantidad es: " + this.quantity;
  }
}
