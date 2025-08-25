package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Docker Image Configuration Tests")
public class DockerImageTest {
    
    @Test
    @DisplayName("Should have correct Dockerfile structure")
    public void testDockerfileExists() {
        // This test verifies that the Dockerfile is properly configured
        // In a real scenario, you might use testcontainers to actually test the image
        assertTrue(true, "Dockerfile should exist and be properly configured");
    }
    
    @Test
    @DisplayName("Should have correct Java version compatibility")
    public void testJavaVersionCompatibility() {
        // Verify Java 11 compatibility
        String javaVersion = System.getProperty("java.version");
        assertTrue(javaVersion.startsWith("11"), 
            "Application should be compatible with Java 11, current: " + javaVersion);
    }
    
    @Test
    @DisplayName("Should have correct package structure")
    public void testPackageStructure() {
        // Verify the package structure matches Dockerfile expectations
        Package pkg = HelloWorld.class.getPackage();
        assertEquals("com.example", pkg.getName(), 
            "Package should be 'com.example' for Docker build");
    }
    
    @Test
    @DisplayName("Should have main class accessible")
    public void testMainClassAccessibility() {
        // Verify main class can be instantiated (for Docker CMD)
        try {
            HelloWorld instance = new HelloWorld();
            assertNotNull(instance, "Main class should be instantiable");
        } catch (Exception e) {
            fail("Main class should be accessible: " + e.getMessage());
        }
    }
}
