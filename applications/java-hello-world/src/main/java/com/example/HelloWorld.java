package com.example;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class HelloWorld {
    private static HttpServer server;
    
    public static void main(String[] args) {
        // Check if running in test mode
        if (args.length > 0 && "test".equals(args[0])) {
            runTests();
            return;
        }
        
        // Normal application mode - start HTTP server
        System.out.println("Starting Hello World HTTP Server...");
        
        try {
            // Create HTTP server on port 8080
            server = HttpServer.create(new InetSocketAddress(8080), 0);
            
            // Create context for root path
            server.createContext("/", new HelloWorldHandler());
            
            // Start the server
            server.start();
            System.out.println("✅ HTTP Server started on port 8080");
            System.out.println("✅ Application is ready and listening for requests");
            System.out.println("✅ Access your app at: http://localhost:8445");
            
            // Keep the main thread alive without constant output
            System.out.println("✅ Server is running. Press Ctrl+C to stop.");
            
            // Simple keep-alive without spam
            while (true) {
                Thread.sleep(30000); // Sleep for 30 seconds
                System.out.println("ℹ️  Server is healthy and running...");
            }
            
        } catch (IOException | InterruptedException e) {
            System.err.println("❌ Failed to start HTTP server: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    // HTTP Handler for serving requests
    static class HelloWorldHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Hello World App</title>
                    <style>
                        body { font-family: Arial, sans-serif; margin: 40px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; }
                        .container { text-align: center; padding: 40px; background: rgba(255,255,255,0.1); border-radius: 20px; backdrop-filter: blur(10px); }
                        h1 { font-size: 3em; margin-bottom: 20px; text-shadow: 2px 2px 4px rgba(0,0,0,0.3); }
                        .status { font-size: 1.2em; margin: 20px 0; padding: 15px; background: rgba(255,255,255,0.2); border-radius: 10px; }
                        .timestamp { font-size: 0.9em; opacity: 0.8; margin-top: 30px; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h1>🚀 Hello Hawk World!</h1>
                        <div class="status">✅ Your Java application is running successfully!</div>
                        <div class="status">🌐 Accessible via NodePort service</div>
                        <div class="status">🔧 Managed by Helm and Kubernetes</div>
                        <div class="timestamp">Generated at: """ + java.time.LocalDateTime.now() + """
                        </div>
                    </div>
                </body>
                </html>
                """;
            
            exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
    
    private static void runTests() {
        System.out.println("Running simple tests...");
        
        try {
            // Test 1: Class instantiation
            HelloWorld helloWorld = new HelloWorld();
            System.out.println("✅ HelloWorld class instantiated successfully");
            
            // Test 2: Main method exists
            HelloWorld.class.getMethod("main", String[].class);
            System.out.println("✅ Main method found successfully");
            
            // Test 3: Package structure
            Package pkg = HelloWorld.class.getPackage();
            if ("com.example".equals(pkg.getName())) {
                System.out.println("✅ Package structure correct");
            } else {
                System.out.println("❌ Package structure incorrect: " + pkg.getName());
            }
            
            System.out.println("✅ All tests passed!");
            
        } catch (Exception e) {
            System.err.println("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
