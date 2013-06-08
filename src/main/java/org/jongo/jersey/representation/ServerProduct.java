package org.jongo.jersey.representation;

import org.jongo.marshall.jackson.oid.ObjectId;

public class ServerProduct {

    @ObjectId
    String _id; // works also with type org.bson.types.ObjectId;

    String name;
    int price;

    ServerProduct() {}
}
