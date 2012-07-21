package org.jongo.jersey.rule;

import static com.sun.jersey.api.core.ResourceConfig.PROPERTY_CONTAINER_REQUEST_FILTERS;
import static com.sun.jersey.api.core.ResourceConfig.PROPERTY_CONTAINER_RESPONSE_FILTERS;
import static com.sun.jersey.api.json.JSONConfiguration.FEATURE_POJO_MAPPING;

import org.glassfish.grizzly.http.server.HttpServer;

import com.sun.jersey.api.container.filter.LoggingFilter;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;

public class GrizzlyServer extends Server {

    HttpServer server;

    @Override
    protected void before() throws Throwable {
        ResourceConfig rc = newConfig();
        server = GrizzlyServerFactory.createHttpServer(uri, rc);
    }

    private ResourceConfig newConfig() {
        ResourceConfig rc = new PackagesResourceConfig("org.jongo.jersey.resource", "org.jongo.jersey.provider");
        rc.getFeatures().put(FEATURE_POJO_MAPPING, true);
        rc.getProperties().put(PROPERTY_CONTAINER_REQUEST_FILTERS, new LoggingFilter());
        rc.getProperties().put(PROPERTY_CONTAINER_RESPONSE_FILTERS, new LoggingFilter());
        return rc;
    }

    @Override
    protected void after() {
        server.stop();
    }
}
