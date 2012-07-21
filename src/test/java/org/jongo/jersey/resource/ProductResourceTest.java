package org.jongo.jersey.resource;

import static com.sun.jersey.api.client.ClientResponse.Status.NOT_FOUND;
import static com.sun.jersey.api.json.JSONConfiguration.FEATURE_POJO_MAPPING;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.fest.assertions.Assertions.assertThat;
import static org.jongo.jersey.view.ProductAssert.assertThat;

import java.util.List;

import org.jongo.jersey.provider.JacksonMapperProvider;
import org.jongo.jersey.rule.Server;
import org.jongo.jersey.view.Product;
import org.jongo.jersey.view.ProductAssert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class ProductResourceTest {

    @ClassRule
    public static Server server = Server.create();
    public static Client client;

    private static String uri = server.uri + "/product";

    @Before
    public void createJacksonCustomClient() {
        ClientConfig cc = new DefaultClientConfig();
        cc.getClasses().add(JacksonMapperProvider.class);
        cc.getFeatures().put(FEATURE_POJO_MAPPING, true);
        client = Client.create(cc);
    }

    @Test
    public void shouldNotGetUnexisting() {
        ClientResponse response = get("1");
        assertThat(response.getStatus()).isEqualTo(NOT_FOUND.getStatusCode());
    }

    @Test
    public void shouldPost() {
        Product product = new Product("pull", 10);
        product = post(product);
        assertThat(product).hasId();

        product = get(product);
        ProductAssert.assertThat(product).hasPrice(10).hasName("pull");
    }

    @Test
    public void shouldPut() {
        Product product = new Product("pull", 10);
        product = post(product);
        assertThat(product).hasId();

        product.setPrice(20);
        put(product);

        product = get(product);
        ProductAssert.assertThat(product).hasPrice(20).hasName("pull");
    }

    @Test
    public void shouldDelete() {
        Product product = new Product("pull", 10);
        product = post(product);
        assertThat(product).isNotNull();

        delete(product);

        ClientResponse response = get(product.getId());
        assertThat(response.getStatus()).isEqualTo(NOT_FOUND.getStatusCode());
    }

    @Test
    public void shouldList() {
        deleteAll();

        post(new Product("pull", 10));
        post(new Product("pull", 10));

        List<Product> products = get();
        assertThat(products).hasSize(2);
    }

    /*
     * Helpers
     */

    public List<Product> get() {
        return client.resource(uri).accept(APPLICATION_JSON).get(new GenericType<List<Product>>() {});
    }

    public Product get(Product product) {
        String id = product.getId();
        return get(id).getEntity(Product.class);
    }

    public ClientResponse get(String id) {
        return client.resource(uri).path(id).accept(APPLICATION_JSON).get(ClientResponse.class);
    }

    public Product post(Product product) {
        return client.resource(uri).accept(APPLICATION_JSON).entity(product, APPLICATION_JSON).post(Product.class);
    }

    public ClientResponse put(Product product) {
        String id = product.getId();
        return client.resource(uri).path(id).accept(APPLICATION_JSON).entity(product, APPLICATION_JSON).put(ClientResponse.class);
    }

    public ClientResponse delete(Product product) {
        String id = product.getId();
        return client.resource(uri).path(id).delete(ClientResponse.class);
    }

    public void deleteAll() {
        List<Product> products = get();
        for (Product product : products)
            delete(product);
    }
}
