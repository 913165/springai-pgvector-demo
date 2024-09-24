package org.example.springaipgvectorsearchdemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {
    private int id;
    @JsonProperty("product_name")
    private String productName;
    private String description;
    private String brand;

    // Constructors
    public Product() {}

    public Product(int id, String productName, String description, String brand) {
        this.id = id;
        this.productName = productName;
        this.description = description;
        this.brand = brand;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    // toString method
    @Override
    public String toString() {
        return "Product{" +
               "id=" + id +
               ", productName='" + productName + '\'' +
               ", description='" + description + '\'' +
               ", brand='" + brand + '\'' +
               '}';
    }
}
