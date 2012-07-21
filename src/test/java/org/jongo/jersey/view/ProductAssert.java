package org.jongo.jersey.view;

import org.fest.assertions.Assertions;
import org.fest.assertions.GenericAssert;

public class ProductAssert extends GenericAssert<ProductAssert, Product> {

    public ProductAssert(Product actual) {
        super(ProductAssert.class, actual);
    }

    public static ProductAssert assertThat(Product actual) {
        return new ProductAssert(actual);
    }

    public ProductAssert hasId() {
        Assertions.assertThat(actual._id).isNotNull();
        return this;
    }

    public ProductAssert hasName(String name) {
        Assertions.assertThat(actual.name).isEqualTo(name);
        return this;
    }

    public ProductAssert hasPrice(int price) {
        Assertions.assertThat(actual.price).isEqualTo(price);
        return this;
    }
}
