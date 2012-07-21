package org.jongo.jersey.representation;

import org.bson.types.ObjectId;

public class ServerProduct {
    ObjectId _id;
    String name;
    int price;

    ServerProduct() {}
}
