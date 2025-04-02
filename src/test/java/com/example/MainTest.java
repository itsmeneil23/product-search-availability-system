package com.example;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class MainTest {
    @Test
    void testMainClassExists() {
        // Verify that the Main class exists and can be instantiated
        Main main = new Main();
        assertNotNull(main, "Main class should be instantiable");
    }
} 