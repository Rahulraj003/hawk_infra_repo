package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HelloWorld Application Tests")
public class HelloWorldTest {
    
    private HelloWorld helloWorld;
    
    @BeforeEach
    void setUp() {
        helloWorld = new HelloWorld();
    }
    
    @Test
    @DisplayName("Should create HelloWorld instance")
    public void testHelloWorldClassExists() {
        assertNotNull(helloWorld);
        assertTrue(helloWorld instanceof HelloWorld);
    }
    
    @Test
    @DisplayName("Should have main method")
    public void testMainMethodExists() {
        try {
            HelloWorld.class.getMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            fail("Main method not found");
        }
    }
    
    @Test
    @DisplayName("Should have correct class name")
    public void testClassName() {
        assertEquals("HelloWorld", HelloWorld.class.getSimpleName());
    }
    
    @Test
    @DisplayName("Should be in correct package")
    public void testPackageName() {
        assertEquals("com.example", HelloWorld.class.getPackage().getName());
    }
    
    @Test
    @DisplayName("Should have public access modifier")
    public void testClassIsPublic() {
        assertTrue(java.lang.reflect.Modifier.isPublic(HelloWorld.class.getModifiers()));
    }
    
    @Test
    @DisplayName("Should have main method as public static")
    public void testMainMethodModifiers() {
        try {
            java.lang.reflect.Method mainMethod = HelloWorld.class.getMethod("main", String[].class);
            assertTrue(java.lang.reflect.Modifier.isPublic(mainMethod.getModifiers()));
            assertTrue(java.lang.reflect.Modifier.isStatic(mainMethod.getModifiers()));
        } catch (NoSuchMethodException e) {
            fail("Main method not found");
        }
    }
}
