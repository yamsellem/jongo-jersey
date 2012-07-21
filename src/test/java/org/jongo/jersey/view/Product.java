package org.jongo.jersey.view;

public class Product {
    String _id;
    String name;
    int price;

    Product() {}

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return _id;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
