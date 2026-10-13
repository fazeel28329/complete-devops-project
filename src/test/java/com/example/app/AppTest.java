package com.example.app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    @Test
    public void testGetMessage() {
        assertEquals(
            "Hello from Complete DevOps Project!",
            App.getMessage()
        );
    }
}
