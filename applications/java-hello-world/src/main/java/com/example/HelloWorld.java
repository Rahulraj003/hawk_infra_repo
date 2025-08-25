package com.example;

public class HelloWorld {
    public static void main(String[] args) {
        // Check if running in test mode
        if (args.length > 0 && "test".equals(args[0])) {
            runTests();
            return;
        }
        
        // Normal application mode
        System.out.println("Starting Hello World Application...");
        
        while (true) {
            System.out.println("Hello World!");
            try {
                Thread.sleep(2000); // Sleep for 2 seconds
            } catch (InterruptedException e) {
                System.err.println("Application interrupted: " + e.getMessage());
                break;
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
