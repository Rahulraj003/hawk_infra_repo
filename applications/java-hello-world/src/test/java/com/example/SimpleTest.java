package com.example;

public class SimpleTest {
    
    public static void main(String[] args) {
        System.out.println("Running simple test...");
        
        // Basic validation
        try {
            HelloWorld helloWorld = new HelloWorld();
            System.out.println("✅ HelloWorld class instantiated successfully");
            
            // Check if main method exists
            HelloWorld.class.getMethod("main", String[].class);
            System.out.println("✅ Main method found successfully");
            
            System.out.println("✅ All tests passed!");
            System.exit(0); // Success
            
        } catch (Exception e) {
            System.err.println("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
            System.exit(1); // Failure
        }
    }
}
