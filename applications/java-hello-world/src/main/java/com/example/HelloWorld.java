package com.example;

public class HelloWorld {
    public static void main(String[] args) {
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
}
