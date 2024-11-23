package com.stqap.swe_qa_project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.stqap.swe_qa_project.service.CalculatorService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorServiceTest {
    
    private CalculatorService calculatorService;
    
    @BeforeEach
    void setUp() {
        calculatorService = new CalculatorService();
    }
    
    @Test
    void calculateMean_ValidInput_ReturnsCorrectMean() {
        // Arrange
        List<Double> values = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        
        // Act
        double result = calculatorService.calculateMean(values);
        
        // Assert
        assertEquals(3.0, result, 0.0001);
    }
    
    @Test
    void calculateMean_EmptyList_ThrowsException() {
        // Arrange
        List<Double> values = Collections.emptyList();
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> calculatorService.calculateMean(values));
    }
}
