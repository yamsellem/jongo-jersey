package org.jongo.jersey.data;

import static com.google.common.collect.Lists.newArrayList;

import java.net.UnknownHostException;
import java.util.List;

import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.jersey.representation.ServerProduct;

import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class Products {

    static MongoCollection products;

    static {
        Mongo mongo;
        try {
            mongo = new Mongo("127.0.0.1", 27017);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (MongoException e) {
            throw new RuntimeException(e);
        }

        Jongo jongo = new Jongo(mongo.getDB("xebia"));
        products = jongo.getCollection("products");
    }

    public static List<ServerProduct> get() {
        return newArrayList(products.find().as(ServerProduct.class));
    }

    public static ServerProduct get(String id) {
        if (ObjectId.isValid(id))
            return products.findOne(new ObjectId(id)).as(ServerProduct.class);
        else
            return null;
    }

    public static ServerProduct put(ServerProduct product) {
        products.save(product);
        return product;
    }

    public static void delete(String id) {
        products.remove(new ObjectId(id));
    }
}
