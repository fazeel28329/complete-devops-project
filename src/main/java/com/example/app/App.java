package com.example.app;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicLong;

public class App {

    private static final AtomicLong requests = new AtomicLong();

    public static String getMessage() {
        return "Hello from Complete DevOps Project!";
    }

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(
            new InetSocketAddress("0.0.0.0", 8082), 0
        );

        server.createContext("/", exchange -> {
            requests.incrementAndGet();
            respond(exchange, 200, getMessage(), "text/plain");
        });

        server.createContext("/health", exchange -> {
            respond(exchange, 200, "UP", "text/plain");
        });

        server.createContext("/metrics", exchange -> {
            String metrics =
                "# HELP app_requests_total Total application requests\n" +
                "# TYPE app_requests_total counter\n" +
                "app_requests_total " + requests.get() + "\n";

            respond(exchange, 200, metrics, "text/plain; version=0.0.4");
        });

        server.start();
        System.out.println("Java web server running on port 8080");
    }

    private static void respond(
        com.sun.net.httpserver.HttpExchange exchange,
        int status,
        String body,
        String contentType
    ) throws java.io.IOException {

        byte[] data = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.sendResponseHeaders(status, data.length);

        try (var output = exchange.getResponseBody()) {
            output.write(data);
        }
    }
}
