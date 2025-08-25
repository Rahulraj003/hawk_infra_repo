package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Timeout;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HelloWorld Integration Tests")
public class HelloWorldIntegrationTest {
    
    @Test
    @DisplayName("Should print Hello World message")
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testHelloWorldOutput() throws InterruptedException {
        // Capture System.out
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        
        try {
            // Start HelloWorld in a separate thread
            Thread helloThread = new Thread(() -> {
                HelloWorld.main(new String[]{});
            });
            helloThread.start();
            
            // Wait a bit for output
            Thread.sleep(3000);
            
            // Stop the thread
            helloThread.interrupt();
            helloThread.join(1000);
            
            // Check output
            String output = outputStream.toString();
            assertTrue(output.contains("Hello World!"), "Output should contain 'Hello World!'");
            assertTrue(output.contains("Starting Hello World Application"), "Output should contain startup message");
            
        } finally {
            // Restore System.out
            System.setOut(originalOut);
        }
    }
    
    @Test
    @DisplayName("Should handle interruption gracefully")
    @Timeout(value = 3, unit = TimeUnit.SECONDS)
    public void testInterruptionHandling() throws InterruptedException {
        // Start HelloWorld in a separate thread
        Thread helloThread = new Thread(() -> {
            HelloWorld.main(new String[]{});
        });
        helloThread.start();
        
        // Wait a bit then interrupt
        Thread.sleep(1000);
        helloThread.interrupt();
        
        // Should stop gracefully
        helloThread.join(1000);
        assertFalse(helloThread.isAlive(), "Thread should stop after interruption");
    }
}
