package org.jongo.jersey.rule;

import org.junit.rules.ExternalResource;

public class Server extends ExternalResource {

    final int port = 9998;
    public final String uri = "http://localhost:" + port;

    public static Server create() {
        return new GrizzlyServer();
    }
}
