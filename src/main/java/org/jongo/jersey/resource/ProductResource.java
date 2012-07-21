package org.jongo.jersey.resource;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.jongo.jersey.data.Products;
import org.jongo.jersey.representation.ServerProduct;

import com.sun.jersey.api.NotFoundException;

@Path("product")
public class ProductResource {
    @GET
    @Path("{id}")
    public Response get(@PathParam("id") String id) {
        ServerProduct product = Products.get(id);

        if (product == null)
            throw new NotFoundException();

        return Response.ok(product).build();
    }

    @PUT
    @Path("{id}")
    public Response put(ServerProduct product) {
        return post(product);
    }

    @POST
    public Response post(ServerProduct product) {
        Products.put(product);
        return Response.ok(product).build();
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") String id) {
        Products.delete(id);
    }

    @GET
    public Response get() {
        List<ServerProduct> products = Products.get();

        GenericEntity<List<ServerProduct>> entity = new GenericEntity<List<ServerProduct>>(products) {};
        return Response.ok(entity).build();
    }
}
